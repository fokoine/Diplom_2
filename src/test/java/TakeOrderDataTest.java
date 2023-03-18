import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import order.data.Ingredients;
import order.data.OrderJsonTemplate;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;

public class TakeOrderDataTest extends PreloadData {

    @Test
    @DisplayName("Получение списка заказов пользователя")
    @Description("Проверка ответа на получение списка заказов с авторизацией")
    public void getOrderListWithAuthText() {
        createNewUser(newUser);
        AccessData accessData = loginRestTest(loginForm).as(AccessData.class);
        OrderJsonTemplate orderJsonTemplate = ingredientsListGet().as(OrderJsonTemplate.class);
        Ingredients ingredients = new Ingredients(new ArrayList<>());
        ingredients.setIngredients(0, orderJsonTemplate.getData().get(0).get_id());
        ingredients.setIngredients(1, orderJsonTemplate.getData().get(1).get_id());
        ingredients.setIngredients(2, orderJsonTemplate.getData().get(2).get_id());
        orderCreateToken(ingredients, accessData.getAccessToken());
        getOrderListAuth(accessData.getAccessToken())
                .then()
                .statusCode(200)
                .body("success",equalTo(true));

    }

    @Test
    @DisplayName("Получение списка заказов без авторищации")
    @Description("Проверка ответа на получение списка заказов без авторизации")
    public void getOrderListWithoutAuthText() {
        getOrderList()
                .then()
                .statusCode(401)
                .body("message",equalTo("You should be authorised"));

    }
}
