package ya.tests;

import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ya.resthandlers.apiclients.ResponseChecks;
import ya.resthandlers.apiclients.UserApiClient;

import java.util.UUID;

import static org.junit.Assert.fail;

@DisplayName("2. Логин пользователя")
@Link(value = "Документация", url = "https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf")
public class LoginUserTests {

    private String email, password, name, token;
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
    }

    @After
    @Step("Очистка тестовых данных")
    public void cleanUp() {
        if (token == null || token.isEmpty()) {
            return;
        }

        checks.verifyStatusCode(userApi.deleteUser(token), 202);
    }

    @Test
    @DisplayName("Авторизация существующего пользователя: успешный вход")
    public void testLoginUserSuccess() {
        Response response = userApi.loginUser(email, password);

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, true);
    }

    @Test
    @DisplayName("Авторизация: некорректный email")
    public void testLoginWithIncorrectEmail() {
        String incorrectEmail = "newE-mail_" + UUID.randomUUID() + "@mail.com";
        Response response = userApi.loginUser(incorrectEmail, password);

        checks.verifyStatusCode(response, 401);
        checks.verifySuccessField(response, false);
        checks.verifyMessageField(response, "email or password are incorrect");
    }

    @Test
    @DisplayName("Авторизация: некорректный пароль")
    public void testLoginWithIncorrectPassword() {
        String incorrectPassword = password + UUID.randomUUID();
        Response response = userApi.loginUser(email, incorrectPassword);

        checks.verifyStatusCode(response, 401);
        checks.verifySuccessField(response, false);
        checks.verifyMessageField(response, "email or password are incorrect");
    }

    @Test
    @DisplayName("Авторизация: отсутствует email")
    public void testLoginWithoutEmail() {
        Response response = userApi.loginUser("", password);

        checks.verifyStatusCode(response, 401);
        checks.verifySuccessField(response, false);
        checks.verifyMessageField(response, "email or password are incorrect");
    }

    @Test
    @DisplayName("Авторизация: отсутствует пароль")
    public void testLoginWithoutPassword() {
        Response response = userApi.loginUser(email, "");

        checks.verifyStatusCode(response, 401);
        checks.verifySuccessField(response, false);
        checks.verifyMessageField(response, "email or password are incorrect");
    }
}