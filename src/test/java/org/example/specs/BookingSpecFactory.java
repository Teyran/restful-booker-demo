package org.example.specs;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.config.AppConfigProvider;
import org.example.utils.CustomAllureListener;

import static io.restassured.RestAssured.given;

public class BookingSpecFactory {

    static {
        RestAssured.baseURI = AppConfigProvider.config.baseUrl();
        RestAssured.filters(CustomAllureListener.withCustomTemplates(), new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    public static RequestSpecification baseRequest() {
        return given().contentType(ContentType.JSON);
    }

    public static RequestSpecification baseRequestWithToken(String token) {
        return baseRequest()
                .header("Cookie", "token=" + token);
    }

}
