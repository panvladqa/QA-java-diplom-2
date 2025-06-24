package ya.resthandlers.httpclients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseHTTPClient {

    /**
     * Отправляет POST-запрос без токена аутентификации.
     */
    public Response sendPostRequest(String url, Object requestBody, String contentType) {
        return given(baseRequest(contentType))
                .body(requestBody)
                .when()
                .post(url);
    }

    /**
     * Отправляет POST-запрос с токеном аутентификации.
     */
    public Response sendPostRequestWithAuth(String url, Object requestBody, String contentType, String token) {
        return given(baseRequest(contentType))
                .auth().oauth2(token)
                .body(requestBody)
                .when()
                .post(url);
    }

    /**
     * Отправляет GET-запрос без токена аутентификации.
     */
    public Response sendGetRequest(String url) {
        return given(baseRequest())
                .get(url);
    }

    /**
     * Отправляет GET-запрос с токеном аутентификации.
     */
    public Response sendGetRequestWithAuth(String url, String token) {
        return given(baseRequest())
                .auth().oauth2(token)
                .when()
                .get(url);
    }

    /**
     * Отправляет DELETE-запрос с токеном аутентификации.
     */
    public Response sendDeleteRequestWithAuth(String url, String token) {
        return given(baseRequest())
                .auth().oauth2(token)
                .delete(url);
    }

    /**
     * Отправляет PATCH-запрос с токеном аутентификации.
     */
    public Response sendPatchRequestWithAuth(String url, Object requestBody, String contentType, String token) {
        return given(baseRequest(contentType))
                .auth().oauth2(token)
                .body(requestBody)
                .when()
                .patch(url);
    }

    /**
     * Создаёт базовую спецификацию запроса без указания типа содержимого.
     */
    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    /**
     * Создаёт базовую спецификацию запроса с указанием типа содержимого.
     */
    private RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-Type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
}