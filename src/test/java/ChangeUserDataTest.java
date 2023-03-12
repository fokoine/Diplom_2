import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends PreloadData{
        @Test
        @DisplayName("Замена данных пользака")
        @Description("Успешность замены данных пользователя")
        public void changeUserDataTest() {
            createNewUser(newUser);
            AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
            changeUserData(userNewData, accessData.getAccessToken())
                    .then()
                    .statusCode(200).body("success",equalTo(true));
            createNewUser(userNewData);
            accessData = loginRestTest(newLoginForm).as(AccessData.class);
            changeUserData(newUser, accessData.getAccessToken());

        }

       @Test
       @DisplayName("Попытка замены на существующую почту")
       @Description("Проверка на отказ в случае если пользователь с аналогичной почтой существует")
        public void repeatChangeUserDataTest() {
            createNewUser(newUser);
            createNewUser(userNewData);
            AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
            changeUserData(userNewData, accessData.getAccessToken())
                   .then().log().all()
                    .statusCode(403).body("message",equalTo("User with such email already exists"));
        }

    @Test
    @DisplayName("Попытка редактуры без авторизации")
    @Description("Попытка редактупы без авторизации пользователем")
    public void changeUserDataWithoutLoginTest() {
        createNewUser(newUser);
        changeUserData(userNewData, "")
                .then()
                .statusCode(401).body("message",equalTo("You should be authorised"));
    }
}