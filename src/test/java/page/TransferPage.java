package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id = amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement to = $("[data-test-id=to] input");
    private SelenideElement depositButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public DashboardPage deposit(int depositAmount, String selectedCard) {
        setAmount(String.valueOf(depositAmount));
        setSelectedCard(selectedCard);
        depositButton.click();
        return new DashboardPage();
    }

    public void setSelectedCard(String selectedCard) {
        from.sendKeys(Keys.CONTROL + "A");
        from.sendKeys(Keys.DELETE);
        from.setValue(selectedCard);
    }

    public void setAmount(String checkSpecialSymbol) {
        amount.sendKeys(Keys.CONTROL + "A");
        amount.sendKeys(Keys.DELETE);
        amount.setValue(Integer.toString(Integer.parseInt(checkSpecialSymbol)));
    }

    public void checkSpecialSymbol() {
        amount.shouldBe(Condition.empty);
    }

    public void checkErrorVisible() {

        errorMessage.shouldBe(Condition.visible);
    }

    public void clickCancel() {
        cancelButton.click();
    }

}
