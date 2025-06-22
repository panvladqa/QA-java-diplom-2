package ya.resthandlers.apiclients;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import ya.responseEntities.UserResponse;

import static org.hamcrest.Matchers.equalTo;

public class ResponseChecks {

    /**
     * Проверяет, что статус-код ответа соответствует ожидаемому.
     */
    @Step("Проверка кода ответа")
    public void verifyStatusCode(Response response, int expectedCode) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(expectedCode);
    }

    /**
     * Проверяет значение поля "success" в ответе.
     */
    @Step("Проверка значения поля 'success'")
    public void verifySuccessField(Response response, String expectedSuccessValue) {
        MatcherAssert.assertThat(
                "Значение поля 'success' не совпадает с ожидаемым",
                expectedSuccessValue,
                equalTo(response.body().as(UserResponse.class).getSuccess())
        );
    }

    /**
     * Проверяет значение поля "message" в ответе.
     */
    @Step("Проверка значения поля 'message'")
    public void verifyMessageField(Response response, String expectedMessageValue) {
        MatcherAssert.assertThat(
                "Значение поля 'message' не совпадает с ожидаемым",
                expectedMessageValue,
                equalTo(response.body().as(UserResponse.class).getMessage())
        );
    }
}