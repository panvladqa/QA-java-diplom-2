package ya.tests;

import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ya.requestEntities.Ingredient;
import ya.responseEntities.IngredientsResponse;
import ya.resthandlers.apiclients.ResponseChecks;
import ya.resthandlers.apiclients.OrderApiClient;
import ya.resthandlers.apiclients.UserApiClient;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;

@DisplayName("5. Получение списка заказов")
@Link(value = "Документация", url = "https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf")
public class OrderListTests {

    private String email, password, name, token;
    private boolean isOrderCreated = false;
    private final OrderApiClient orderApi = new OrderApiClient();
    private final UserApiClient userApi = new UserApiClient();
    private final ResponseChecks checks = new ResponseChecks();

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        email = "e-mail_" + UUID.randomUUID() + "@mail.com";
        password = "pass";
        name = "name";

        Response createUserResponse = userApi.createUser(email, password, name);
        checks.verifyStatusCode(createUserResponse, 200);

        if (createUserResponse.getStatusCode() == 200) {
            token = userApi.extractAccessToken(createUserResponse);
        }

        if (token == null) {
            fail("Не удалось создать тестового пользователя");
        }

        Response getIngredientsResponse = orderApi.getAvailableIngredients();
        checks.verifyStatusCode(getIngredientsResponse, 200);

        IngredientsResponse ingredientsResponse = getIngredientsResponse.body().as(IngredientsResponse.class);
        List<Ingredient> ingredients = ingredientsResponse.getData();

        if (ingredients == null || ingredients.isEmpty()) {
            fail("Список ингредиентов пуст");
        }

        if (ingredients.isEmpty()) {
            fail("Список ингредиентов пуст после фильтрации");
        }
        for (Ingredient ingredient : ingredients) {
            if (ingredient == null || ingredient.getId() == null) {
                fail("Найден null в списке ингредиентов");
            }
        }
        Response createOrderResponse = orderApi.createNewOrder(
                List.of(ingredients.get(0).getId(), ingredients.get(ingredients.size() - 1).getId()),
                token
        );

        checks.verifyStatusCode(createOrderResponse, 200);

        if (createOrderResponse.getStatusCode() == 200) {
            isOrderCreated = true;
        }
    }
    @After
    @Step("Очистка тестовых данных")
    public void cleanUp() {
        if (token == null) {
            return;
        }

        checks.verifyStatusCode(userApi.deleteUser(token), 202);
    }

    @Test
    @DisplayName("Получение списка заказов: авторизованный пользователь")
    public void getOrderListWithAuthIsSuccess() {
        if (token == null || !isOrderCreated) {
            fail("Не создан тестовый пользователь или заказ");
        }

        Response response = orderApi.getUserOrders(token);

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, "true");
    }

    @Test
    @DisplayName("Получение списка заказов: неавторизованный пользователь")
    public void getOrderListWithoutAuthIsFailed() {
        if (token == null || !isOrderCreated) {
            fail("Не создан тестовый пользователь или заказ");
        }

        Response response = orderApi.getUserOrders("");

        checks.verifyStatusCode(response, 401);
        checks.verifySuccessField(response, "false");
        checks.verifyMessageField(response, "You should be authorised");
    }

    @Test
    @DisplayName("Получение списка всех заказов")
    public void getOrderListAllIsSuccess() {
        Response response = orderApi.getAllOrders();

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, "true");
    }
}