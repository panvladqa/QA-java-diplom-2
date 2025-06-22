package ya.resthandlers.httpclients;

import io.restassured.response.Response;
import ya.ApiUrls;
import ya.requestEntities.User;

public class UserHTTPClient extends BaseHTTPClient {

    /**
     * Создает нового пользователя, отправляя POST-запрос на эндпоинт создания пользователя.
     */
    public Response registerUser(User user) {
        return sendPostRequest(
                ApiUrls.HOST + ApiUrls.CREATE_USER,
                user,
                "application/json"
        );
    }

    /**
     * Удаляет существующего пользователя, отправляя DELETE-запрос на эндпоинт удаления пользователя.
     */
    public Response removeUser(String token) {
        return sendDeleteRequestWithAuth(
                ApiUrls.HOST + ApiUrls.USER,
                token
        );
    }

    /**
     * Авторизует пользователя, отправляя POST-запрос на эндпоинт входа в систему.
     */
    public Response authenticateUser(User user) {
        return sendPostRequest(
                ApiUrls.HOST + ApiUrls.LOGIN_USER,
                user,
                "application/json"
        );
    }

    /**
     * Обновляет данные пользователя, отправляя PATCH-запрос на эндпоинт обновления профиля.
     */
    public Response updateUserProfile(User user, String token) {
        return sendPatchRequestWithAuth(
                ApiUrls.HOST + ApiUrls.USER,
                user,
                "application/json",
                token
        );
    }
}