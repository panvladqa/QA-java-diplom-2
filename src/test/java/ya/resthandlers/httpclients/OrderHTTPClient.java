package ya.resthandlers.httpclients;

import io.restassured.response.Response;
import ya.ApiUrls;
import ya.request.entities.Order;

public class OrderHTTPClient extends BaseHTTPClient {

    /**
     * Создаёт новый заказ, отправляя POST-запрос на эндпоинт создания заказа.
     */
    public Response createNewOrder(Order order, String token) {
        return sendPostRequestWithAuth(
                ApiUrls.HOST + ApiUrls.ORDERS,
                order,
                "application/json",
                token
        );
    }

    /**
     * Получает список доступных ингредиентов, отправляя GET-запрос на соответствующий эндпоинт.
     */
    public Response getAvailableIngredients() {
        return sendGetRequest(
                ApiUrls.HOST + ApiUrls.INGREDIENTS
        );
    }

    /**
     * Получает список всех заказов текущего пользователя, отправляя GET-запрос на эндпоинт списка заказов.
     */
    public Response getUserOrders(String token) {
        return sendGetRequestWithAuth(
                ApiUrls.HOST + ApiUrls.ORDERS,
                token
        );
    }

    /**
     * Получает список всех заказов (не только текущего пользователя), отправляя GET-запрос на эндпоинт общего списка заказов.
     */
    public Response getAllOrders() {
        return sendGetRequest(
                ApiUrls.HOST + ApiUrls.ORDERS_ALL
        );
    }
}