import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends PreloadData{
        @Test
        @DisplayName("Замена данных пользака")
        @Description("Успешность замены данных пользователя")
        public void changeUserDataTest() {
            createNewUser(newUser);
            AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
            UserResponseTemplate userResponseTemplate = changeUserData(userNewData, accessData.getAccessToken()).as(UserResponseTemplate.class);
            accessData = loginRestTest(newLoginForm).as(AccessData.class);
            Assert.assertEquals(accessData.getUser().getEmail(), userResponseTemplate.getUser().getEmail());
            Assert.assertEquals(accessData.getUser().getName(), userResponseTemplate.getUser().getName());
            // Доп действие для данного кейса по удалению данных
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
                    .then()
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