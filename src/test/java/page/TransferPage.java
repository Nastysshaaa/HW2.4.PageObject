package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    public SelenideElement heading = $(withText("Пополнение карты"));
public SelenideElement amount = $ ( "[data-test-id = amount] input");
public SelenideElement from = $ ( "[data-test-id=from] input");
public SelenideElement to = $ ("[data-test-id=to] input");
public SelenideElement depositButton = $ ("[data-test-id=action-transfer]");
public SelenideElement cancelButton = $ ("[data-test-id=action-cancel]");
public SelenideElement errorMessage = $ ("[data-test-id=error-notification]");

public DashboardPage deposit (int depositAmount, String selectedCard) {
    setAmount(depositAmount);
    setSelectedCard(selectedCard);
    depositButton.click();
    return new DashboardPage();
}
    public void setSelectedCard(String selectedCard) {
    from.sendKeys(Keys.CONTROL+"A");
    from.sendKeys(Keys.DELETE);
    from.setValue(selectedCard);
    }

    private void setAmount(int depositAmount) {
        amount.sendKeys(Keys.CONTROL+"A");
        amount.sendKeys(Keys.DELETE);
        amount.setValue(Integer.toString(depositAmount));
    }
    public void checkErrorVisible() {
        errorMessage.shouldBe(Condition.visible);
    }
    public void clickCancel() {
        cancelButton.click();
    }

}
