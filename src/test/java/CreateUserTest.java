import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends PreloadData {

    @Test
    @DisplayName("Валидное создание пользака")
    @Description("Создание пользователя валидное, хэппи флоу")
    public void createCourierValidTest() {
        createNewUser(newUser)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Создание повторки")
    @Description("Попытка создать пользака с уже существующими данными")
    public void createCourierDuplicateTest() {
        createNewUser(newUser);
        createNewUser(newUser)
                .then()
                .statusCode(403)
                .body("message",equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание без полей")
    @Description("Тест на НЕсоздание в случае отсутствия каких-либо данных")
    public void createCourierInvalidTest() {
        createNewUser(newUserInvalid)
                .then()
                .statusCode(403)
                .body("message",equalTo("Email, password and name are required fields"));
    }

}