package org.example.client;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestResponse<T> {

    private final ValidatableResponse response;
    private final Class<T> clazz;

    public T asObject() {
        return response.extract().as(clazz);
    }

    public List<T> asList(){
        return response.extract().jsonPath().getList("", clazz);
    }

    public String asString() {
        return response.extract().asPrettyString();
    }

    public Response asResponse() {
        return response.extract().response();
    }

    public RestResponse<T> checkStatusCode(int status) {
        response.statusCode(status);
        return this;
    }
}
