package ya.resthandlers.apiclients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ya.request.entities.Order;
import ya.resthandlers.httpclients.OrderHTTPClient;

import java.util.List;

/**
 * Клиент API для работы с заказами.
 */
public class OrderApiClient extends OrderHTTPClient {

    /**
     * Отправляет запрос на создание нового заказа.
     */
    @Step("Отправка запроса на создание заказа")
    public Response createNewOrder(List<String> ingredients, String token) {
        return super.createNewOrder(new Order(ingredients), token);
    }

    /**
     * Получает список доступных ингредиентов.
     */
    @Step("Получение списка ингредиентов")
    public Response getAvailableIngredients() {
        return super.getAvailableIngredients();
    }

    /**
     * Получает список заказов текущего пользователя.
     */
    @Step("Получение списка заказов пользователя")
    public Response getUserOrders(String token) {
        return super.getUserOrders(token);
    }

    /**
     * Получает список всех заказов (не только текущего пользователя).
     */
    @Step("Получение списка всех заказов")
    public Response getAllOrders() {
        return super.getAllOrders();
    }
}