package test;

import data.DataHelper;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;
import org.openqa.selenium.Keys;
import data.DataHelper;

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
    void transferFirstCard() {
        int actual1 = dashboardPage.depositFirstCard().deposit(500, DataHelper.secondCardNumber())
                .getFirstCardBalance();
        Assertions.assertEquals(10500, actual1);
        int actual2 = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(9500, actual2);
    }


}
