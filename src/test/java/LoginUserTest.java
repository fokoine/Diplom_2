import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends PreloadData{

    @Test
    @DisplayName("Авторизация")
    @Description("Тест на валидную авторизацию")
    public void loginTestValid() {
        createNewUser(newUser);
        loginRestTest(loginForm)
                .then()
                .statusCode(200).body("success",equalTo(true));
    }

    @Test
    @DisplayName("Невалидная авторизация")
    @Description("Прерывание авторизации в случае некорректных данных")
    public void loginTestInvalidLoginData() {
        createNewUser(newUser);
        loginRestTest(loginFormInvalid)
                .then().log().all()
                .statusCode(401).body("message",equalTo("email or password are incorrect"));
        loginRestTest(loginForm);
    }


}
