package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    private void successAuth() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getValidVerificationCodeFor();
        dashboardPage = verificationPage.validVerify(verificationCode);
        setInitialBalances(dashboardPage);
    }

    private void setInitialBalances(DashboardPage dashboardPage) {

        int currentBalance = dashboardPage.getFirstCardBalance();
        int depositBalance = 10000 - currentBalance;
        if (depositBalance > 0) {
            dashboardPage.depositFirstCard().deposit(depositBalance, DataHelper.secondCardNumber());
        } else if (depositBalance < 0) {
            dashboardPage.depositSecondCard().deposit(-depositBalance, DataHelper.firstCardNumber());
        }
    }

    @Test
    void transferToFirstCard() {
        int actual1 = dashboardPage.depositFirstCard().deposit(500, DataHelper.secondCardNumber())
                .getFirstCardBalance();
        Assertions.assertEquals(10500, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(9500, actual2);
    }

    @Test
    void transferToSecondCard() {
        int actual1 = dashboardPage.depositSecondCard().deposit(1500, DataHelper.firstCardNumber())
                .getSecondCardBalance();
        Assertions.assertEquals(11500, actual1);
        int actual2 = dashboardPage.getFirstCardBalance();
        Assertions.assertEquals(8500, actual2);
    }

    @Test
    void transferAllAmountFromFirstCard() {
        int actual1 = dashboardPage.depositSecondCard().deposit(10000, DataHelper.firstCardNumber())
                .getSecondCardBalance();
        Assertions.assertEquals(20_000, actual1);
        int actual2 = dashboardPage.getFirstCardBalance();
        Assertions.assertEquals(0, actual2);
    }

    @Test
    void transferAllAmountFromSecondCard() {
        int actual1 = dashboardPage.depositFirstCard().deposit(10000, DataHelper.secondCardNumber())
                .getFirstCardBalance();
        Assertions.assertEquals(20_000, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(0, actual2);
    }

    @Test
    void transferFromFirstCardToFirstCard() {
        int actual1 = dashboardPage.depositFirstCard().deposit(400, DataHelper.firstCardNumber())
                .getFirstCardBalance();
        Assertions.assertEquals(10_000, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(10_000, actual2);
    }

    @Test
    void transferFromFirstCardToSecondCard() {
        int actual1 = dashboardPage.depositSecondCard().deposit(400, DataHelper.secondCardNumber())
                .getSecondCardBalance();
        Assertions.assertEquals(10_000, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(10_000, actual2);
    }

    @Test
    void transferAmountEqualBoundaryAmountByFirstCard() {
        int actual1 = dashboardPage.depositSecondCard().deposit(9999, DataHelper.firstCardNumber())
                .getSecondCardBalance();
        Assertions.assertEquals(19_999, actual1);
        int actual2 = dashboardPage.getFirstCardBalance();
        Assertions.assertEquals(1, actual2);
    }

    @Test
    void transferAmountEqualBoundaryAmountBySecondCard() {
        int actual1 = dashboardPage.depositFirstCard().deposit(9999, DataHelper.secondCardNumber())
                .getFirstCardBalance();
        Assertions.assertEquals(19_999, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(1, actual2);
    }

    @Test
    void emptyFieldFromFirstCard() {
        dashboardPage.depositFirstCard().deposit(500, "");
        new TransferPage().checkErrorVisible();
    }

    @Test
    void emptyFieldFromSecondCard() {
        dashboardPage.depositSecondCard().deposit(500, "");
        new TransferPage().checkErrorVisible();
    }

    @Test
    void emptyFieldAmount() {
        int actual1 = dashboardPage.depositFirstCard().deposit(0, DataHelper.secondCardNumber())
                .getFirstCardBalance();
        Assertions.assertEquals(10_000, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(10_000, actual2);
    }

    @Test
    void cancelClick() {
        TransferPage transferPage = dashboardPage.depositFirstCard();
        transferPage.setAmount(String.valueOf(200));
        transferPage.setSelectedCard(DataHelper.secondCardNumber());
        transferPage.clickCancel();
        int actual1 = dashboardPage.getFirstCardBalance();
        Assertions.assertEquals(10_000, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(10_000, actual2);
    }

    @Test
    void invalidCardNumber() {
        dashboardPage.depositSecondCard().deposit(200, DataHelper.invalidCardNumber());
        new TransferPage().checkErrorVisible();
    }

    @Test
    void checkSpecialSymbolInAmountField() {
        TransferPage transferPage = dashboardPage.depositFirstCard();
        transferPage.setAmount("@#$");
        transferPage.checkSpecialSymbol();
    }

    @Test
    void transferAmountMoreThanInFirstCard() {
        int actual1 = dashboardPage.depositSecondCard().deposit(11_000, DataHelper.firstCardNumber())
                .getSecondCardBalance();
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(10_000, actual2);
        Assertions.assertEquals(10_000, actual1);
    }

    @Test
    void transferAmountMoreThanInSecondCard() {
        int actual1 = dashboardPage.depositFirstCard().deposit(15_000, DataHelper.secondCardNumber())
                .getFirstCardBalance();
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(10_000, actual2);
        Assertions.assertEquals(10_000, actual1);
    }
}
