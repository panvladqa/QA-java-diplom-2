package ya.tests;

import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ya.request.entities.Ingredient;
import ya.response.entities.IngredientsResponse;
import ya.resthandlers.apiclients.ResponseChecks;
import ya.resthandlers.apiclients.OrderApiClient;
import ya.resthandlers.apiclients.UserApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;

@DisplayName("4. Создание заказа")
@Link(value = "Документация", url = "https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf")
public class CreateOrderTests {

    private String email, password, name, token;
    private List<Ingredient> ingredients = new ArrayList<>();
    private final OrderApiClient orderApi = new OrderApiClient();
    private final UserApiClient userApi = new UserApiClient();
    private final ResponseChecks checks = new ResponseChecks();

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        // Генерация уникального email и пароля
        email = "e-mail_" + UUID.randomUUID() + "@mail.com";
        password = "pass";
        name = "name";

        // Создание пользователя
        Response createUserResponse = userApi.createUser(email, password, name);
        checks.verifyStatusCode(createUserResponse, 200);

        // Получение токена
        if (createUserResponse.getStatusCode() == 200) {
            token = userApi.extractAccessToken(createUserResponse);
        }

        if (token == null) {
            fail("Не удалось создать тестового пользователя");
        }

        Response getIngredientsResponse = orderApi.getAvailableIngredients();
        checks.verifyStatusCode(getIngredientsResponse, 200);

        IngredientsResponse ingredientsResponse = getIngredientsResponse.body().as(IngredientsResponse.class);
        this.ingredients = ingredientsResponse.getData();
        if (this.ingredients == null || this.ingredients.isEmpty()) {
            fail("Список ингредиентов пуст");
        }

        for (Ingredient ingredient : this.ingredients) {
            if (ingredient == null || ingredient.getId() == null) {
                fail("Список ингредиентов пуст. Ответ API: " + getIngredientsResponse.asString());
            }
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
    @DisplayName("Создание заказа: с авторизацией и с ингредиентами")
    public void testCreateOrderWithAuthAndIngredients() {
        List<String> ingredientIds = List.of(
                ingredients.get(0).getId(),
                ingredients.get(ingredients.size() - 1).getId()
        );

        Response response = orderApi.createNewOrder(ingredientIds, token);

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, true);
    }

    @Test
    @DisplayName("Создание заказа: без авторизации и с ингредиентами")
    public void testCreateOrderWithoutAuthAndWithIngredients() {
        List<String> ingredientIds = List.of(
                ingredients.get(0).getId(),
                ingredients.get(ingredients.size() - 1).getId()
        );

        Response response = orderApi.createNewOrder(ingredientIds, "");

        checks.verifyStatusCode(response, 200);
    }

    @Test
    @DisplayName("Создание заказа: с авторизацией и без ингредиентов")
    public void testCreateOrderWithAuthAndWithoutIngredients() {
        Response response = orderApi.createNewOrder(new ArrayList<>(), token);

        checks.verifyStatusCode(response, 400);
        checks.verifySuccessField(response, false);
        checks.verifyMessageField(response, "Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Создание заказа: с неверным идентификатором ингредиента")
    public void testCreateOrderWithIncorrectIngredients() {
        List<String> ingredientIds = List.of(
                ingredients.get(0).getId(),
                UUID.randomUUID().toString()
        );

        Response response = orderApi.createNewOrder(ingredientIds, token);

        checks.verifyStatusCode(response, 500);
    }
}