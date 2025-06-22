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

import java.util.ArrayList;
import java.util.UUID;

@DisplayName("1. Создание пользователя")
@Link(value = "Документация", url = "https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf")
public class CreateUserTests {

    private String email, password, name;
    private final ArrayList<String> tokens = new ArrayList<>();
    private final UserApiClient userApi = new UserApiClient();
    private final ResponseChecks checks = new ResponseChecks();

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        // Генерация уникального email и пароля
        email = "e-mail_" + UUID.randomUUID() + "@mail.com";
        password = "pass_" + UUID.randomUUID();
        name = "name";
    }

    @After
    @Step("Очистка тестовых данных")
    public void cleanUp() {
        if (tokens.isEmpty()) {
            return;
        }

        for (String token : tokens) {
            checks.verifyStatusCode(userApi.deleteUser(token), 202);
        }
    }

    @Test
    @DisplayName("Создание пользователя: успешное создание")
    public void createNewUserIsSuccess() {
        Response response = userApi.createUser(email, password, name);

        if (response.getStatusCode() == 200) {
            tokens.add(userApi.extractAccessToken(response));
        }

        checks.verifyStatusCode(response, 200);
        checks.verifySuccessField(response, "true");
    }

    @Test
    @DisplayName("Создание двух одинаковых пользователей: второй должен провалиться")
    public void createNewSimilarUsersIsFailed() {
        Response firstUserResponse = userApi.createUser(email, password, name);
        Response secondUserResponse = userApi.createUser(email, password, name);

        if (firstUserResponse.getStatusCode() == 200) {
            tokens.add(userApi.extractAccessToken(firstUserResponse));
        }

        checks.verifyStatusCode(secondUserResponse, 403);
        checks.verifySuccessField(secondUserResponse, "false");
        checks.verifyMessageField(secondUserResponse, "User already exists");
    }

    @Test
    @DisplayName("Создание пользователя: без email")
    public void createNewUserMissedEmailIsFailed() {
        Response response = userApi.createUser("", password, name);

        if (response.getStatusCode() == 200) {
            tokens.add(userApi.extractAccessToken(response));
        }

        checks.verifyStatusCode(response, 403);
        checks.verifySuccessField(response, "false");
        checks.verifyMessageField(response, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя: без пароля")
    public void createNewUserMissedPasswordIsFailed() {
        Response response = userApi.createUser(email, "", name);

        if (response.getStatusCode() == 200) {
            tokens.add(userApi.extractAccessToken(response));
        }

        checks.verifyStatusCode(response, 403);
        checks.verifySuccessField(response, "false");
        checks.verifyMessageField(response, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя: без имени")
    public void createNewUserMissedNameIsFailed() {
        Response response = userApi.createUser(email, password, "");

        if (response.getStatusCode() == 200) {
            tokens.add(userApi.extractAccessToken(response));
        }

        checks.verifyStatusCode(response, 403);
        checks.verifySuccessField(response, "false");
        checks.verifyMessageField(response, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Создание пользователя: без всех полей")
    public void createNewUserMissedAllParamsIsFailed() {
        Response response = userApi.createUser("", "", "");

        if (response.getStatusCode() == 200) {
            tokens.add(userApi.extractAccessToken(response));
        }

        checks.verifyStatusCode(response, 403);
        checks.verifySuccessField(response, "false");
        checks.verifyMessageField(response, "Email, password and name are required fields");
    }
}