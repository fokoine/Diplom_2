import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import order.data.Ingredients;
import order.data.OrderJsonTemplate;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends PreloadData {

    @Test
    @DisplayName("Создание заказа с авторизацией")
    @Description("Создание заказа на булку с автоиризацией в системе")
    public void createOrderWithAuthTest() {
        createNewUser(newUser);
        AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
        OrderJsonTemplate orderJsonTemplate = ingredientsListGet().as(OrderJsonTemplate.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        ingredients.setIngredients(0, orderJsonTemplate.getData().get(0).get_id());
        ingredients.setIngredients(1, orderJsonTemplate.getData().get(1).get_id());
        ingredients.setIngredients(2, orderJsonTemplate.getData().get(2).get_id());
        orderCreateToken(ingredients, accessData.getAccessToken())
                .then()
                .statusCode(200)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Создание заказа на булку без автоиризации в системе")
    public void createOrderWithoutAuthTest() {
        OrderJsonTemplate orderJsonTemplate = ingredientsListGet().as(OrderJsonTemplate.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        ingredients.setIngredients(0, orderJsonTemplate.getData().get(0).get_id());
        ingredients.setIngredients(1, orderJsonTemplate.getData().get(1).get_id());
        ingredients.setIngredients(2, orderJsonTemplate.getData().get(2).get_id());
        orderCreate(ingredients)
                .then()
                .statusCode(200)
                .body("success",equalTo(true));;
    }

    @Test
    @DisplayName("Без авторизаци и ингредиентов")
    @Description("Создание заказа на булку без автоиризации и ингредиентов в системе")
    public void createOrderWithoutAuthAndRegsTest() {
        OrderJsonTemplate orderJsonTemplate = ingredientsListGet().as(OrderJsonTemplate.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        orderCreate(ingredients)
                .then()
                .statusCode(400)
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("С авторизацией и без ингредиентов")
    @Description("Создание заказа на булку с автоиризацией и без ингредиентов")
    public void createOrderWithAuthAndWithoutRegsTest() {
        createNewUser(newUser);
        AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        orderCreateToken(ingredients, accessData.getAccessToken())
                .then()
                .statusCode(400)
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с битым ингредиентом")
    @Description("Создание с битым id ингредиента")
    public void createOrderWithIncorrectHash() {
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        String incorrectReg = "incorrect reg";
        ingredients.setIngredients(0, incorrectReg);
        orderCreate(ingredients)
                .then()
                .statusCode(500);
    }
}
