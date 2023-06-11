package page;

import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
        public VerificationPage validLogin (DataHelper.AuthInfo info) { //передаем данные для входа
            $("[data-test-id=login] input").setValue(info.getLogin());
            $("[data-test-id=password] input").setValue(info.getPassword());
            $("[data-test-id=action-login]").click();
            return new VerificationPage(); //после заполнения формы и клик, мы ожидаем, что откроется verification Page, вызываем конструктор
        }
    }

