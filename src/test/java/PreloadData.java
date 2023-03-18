import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.data.Ingredients;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class PreloadData {

    UserData userData = new UserData();
    String newUser = "{\"email\": \"" + userData.randomEmail + "@email.ru\", \"password\": \"" + userData.randomPassword + "\", \"name\": \"" + userData.randomUsername + "\"}";
    String userNewData = "{\"email\": \"" + userData.newRandomEmail + "@email.ru\", \"password\": \"" + userData.newRandomPassword + "\", \"name\": \"" + userData.newRandomUsername + "\"}";
    String newUserInvalid = "{\"email\": \"" + userData.randomEmail + "@email.ru\", \"password\": \"" + userData.randomPassword + "\", \"name\": \"\"}";
    String loginForm = "{\"email\": \"" + userData.randomEmail + "@email.ru\", \"password\": \"" + userData.randomPassword + "\"}";
    String newLoginForm = "{\"email\": \"" + userData.newRandomEmail + "@email.ru\", \"password\": \"" + userData.newRandomPassword + "\"}";
    String loginFormInvalid = "{\"email\": \"" + userData.randomPassword + "\", \"password\": \"" + userData.randomUsername + "\"}";
    // Метод регистрации пользака
    public Response createNewUser(String userData) {
        Response response = given().header("Content-type", "application/json").and().body(userData).post("api/auth/register");
        return response;
    }
    // Метод авторизации
    public Response loginRestTest(String loginPresetForm){
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .body(loginPresetForm)
                .post("/api/auth/login");
        return loginResponse;
    }
    // Метод изменения данных
    public Response changeUserData(String userDataForChange, String tokenData){
        Response changeResponse = given()
                .header("authorization", tokenData)
                .header("Content-type", "application/json")
                .body(userDataForChange)
                .patch("/api/auth/user");
        return changeResponse;
    }
    // Разлогин пользака
    public Response logoutUser(String logoutToken){
        Response logoutResponse = given()
                .header("Content-type", "application/json")
                .body("{\"token\": \"" + logoutToken + "\"}")
                .post("/api/auth/logout");
        return logoutResponse;
    }
    // Получение списка ингредиентов
    public Response ingredientsListGet(){
        Response createResponse = given()
                .header("Content-type", "application/json")
                .get("/api/ingredients");
        return createResponse;
    }

    // Создание заказа без токена
    public Response orderCreate(Ingredients ingredients){
        Response createResponse = given()
                .header("Content-type", "application/json")
                .body(ingredients)
                .post("/api/orders");
        return createResponse;
    }

    // Создание заказа с токеном
    public Response orderCreateToken(Ingredients ingredients, String token){
        Response createResponse = given()
                .header("Content-type", "application/json")
                .header("authorization", token)
                .body(ingredients)
                .post("/api/orders");
        return createResponse;
    }

    // Получение списка заказов
    public Response getOrderList(){
        Response createResponse = given()
                .header("Content-type", "application/json")
                .get("/api/orders");
        return createResponse;
    }

    // Получение списка заказов с токеном
    public Response getOrderListAuth(String tokenData){
        Response createResponse = given()
                .header("Content-type", "application/json")
                .header("authorization", tokenData)
                .get("/api/orders");
        return createResponse;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    // Удаление пользака после тестов если он был создан
    public void deleteUser() {
        AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
        if (accessData.getAccessToken() != null) {
            given().header("authorization", accessData.getAccessToken()).and().body(newUser).delete("api/auth/user");
        } else {
            accessData = loginRestTest(newLoginForm).as(AccessData.class);
            if (accessData.getAccessToken() != null) {
                given().header("authorization", accessData.getAccessToken()).and().body(userNewData).delete("api/auth/user");
            }
        }
    }

}