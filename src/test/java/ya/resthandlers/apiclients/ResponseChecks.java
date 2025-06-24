package ya.resthandlers.apiclients;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import ya.response.entities.UserResponse;

import static org.hamcrest.Matchers.equalTo;

public class ResponseChecks {

    @Step("Проверка кода ответа")
    public void verifyStatusCode(Response response, int expectedCode) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(expectedCode);
    }

    @Step("Проверка значения поля 'success'")
    public void verifySuccessField(Response response, boolean expectedSuccessValue) {
        boolean actualSuccess = response.jsonPath().getBoolean("success");
        MatcherAssert.assertThat(
                "Значение поля 'success' не совпадает с ожидаемым",
                actualSuccess,
                equalTo(expectedSuccessValue)
        );
    }


    @Step("Проверка значения поля 'message'")
    public void verifyMessageField(Response response, String expectedMessageValue) {
        String actualMessage = response.body().as(UserResponse.class).getMessage();
        MatcherAssert.assertThat(
                "Значение поля 'message' не совпадает с ожидаемым",
                actualMessage,
                equalTo(expectedMessageValue)
        );
    }
}