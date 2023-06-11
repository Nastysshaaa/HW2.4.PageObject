package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input"); //поиск элементов вынесен в отдельные поля и в методе используются именно эти поля
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage() {
        codeField.shouldBe(visible); //определен конструктор и проверяем что есть поле ввода кода, Selenide библиотека по умолчанию ждет 4 секунды, страница должна открыться
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        return null;
    }
}
