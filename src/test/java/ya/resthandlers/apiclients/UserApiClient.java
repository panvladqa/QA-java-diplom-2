package ya.resthandlers.apiclients;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import ya.request.entities.User;
import ya.response.entities.UserResponse;
import ya.resthandlers.httpclients.UserHTTPClient;

import static org.hamcrest.Matchers.equalTo;

public class UserApiClient extends UserHTTPClient {

    /**
     * Отправляет запрос на создание нового пользователя.
     */
    @Step("Отправка запроса на создание пользователя")
    public Response createUser(String email, String password, String name) {
        return super.registerUser(new User(email, password, name));
    }

    /**
     * Отправляет запрос на авторизацию пользователя.
     */
    @Step("Отправка запроса на логин пользователя")
    public Response loginUser(String email, String password) {
        return super.authenticateUser(new User(email, password));
    }

    /**
     * Отправляет запрос на удаление пользователя.
     */
    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        return super.removeUser(token);
    }

    /**
     * Отправляет запрос на обновление данных пользователя.
     */
    @Step("Обновление информации о пользователе")
    public Response updateUser(String email, String password, String name, String token) {
        return super.updateUserProfile(new User(email, password, name), token);
    }

    /**
     * Проверяет данные пользователя в ответе.
     */
    @Step("Проверка данных пользователя")
    public void verifyUserData(Response response, String expectedEmail, String expectedName) {
        User actualUser = response.body().as(UserResponse.class).getUser();
        Allure.addAttachment("Пользователь", actualUser.toString());

        MatcherAssert.assertThat("Не совпадают email-ы", actualUser.getEmail(), equalTo(expectedEmail));
        MatcherAssert.assertThat("Не совпадают имена", actualUser.getName(), equalTo(expectedName));

        new ResponseChecks().verifyStatusCode(loginUser(expectedEmail, "wrong_password"), 401);
    }

    /**
     * Извлекает токен из ответа.
     */
    @Step("Получение токена авторизации")
    public String extractAccessToken(Response response) {
        String token = response.body().as(UserResponse.class).getAccessToken().split(" ")[1];
        Allure.addAttachment("Ответ", response.getStatusLine());
        Allure.addAttachment("Токен", token);
        return token;
    }
}