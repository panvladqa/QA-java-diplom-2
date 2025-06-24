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

@DisplayName("3. Изменение данных пользователя")
@Link(value = "Документация", url = "https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf")
public class ChangeUserDataTests {

    private String email, password, name, token;
    private final ResponseChecks checks = new ResponseChecks();
    private final UserApiClient userApi = new UserApiClient();

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        // Генерация уникального email
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
            fail("Тестовый пользователь не создан");
        }
    }

    @After
    @Step("Удаление тестового пользователя")
    public void cleanUp() {
        if (token == null) {
            return;
        }

        checks.verifyStatusCode(userApi.deleteUser(token), 202);
    }

    @Test
    @DisplayName("Изменение данных пользователя: с авторизацией")
    public void testChangeUserDataWithAuth() {
        String newEmail = "new_" + email;
        String newPassword = "new_" + password;
        String newName = "new_" + name;

        Response response = userApi.updateUser(newEmail, newPassword, newName, token);

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, true);
        userApi.verifyUserData(response, newEmail, newName);
    }

    @Test
    @DisplayName("Изменение данных пользователя: с авторизацией, поля без изменений")
    public void testChangeUserDataWithAuthWhenSendSameData() {
        Response response = userApi.updateUser(email, password, name, token);

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, true);
        checks.verifyMessageField(response, null);

        userApi.verifyUserData(response, email, name);
    }

    @Test
    @DisplayName("Изменение данных пользователя: без авторизации")
    public void testChangeUserDataWithoutAuth() {
        String newEmail = "new_" + email;
        String newPassword = "new_" + password;
        String newName = "new_" + name;

        Response response = userApi.updateUser(newEmail, newPassword, newName, "");

        checks.verifyStatusCode(response, 401);
        checks.verifySuccessField(response, false);
        checks.verifyMessageField(response, "You should be authorised");
    }
}