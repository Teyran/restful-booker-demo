package org.example.client;

import io.qameta.allure.Step;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.config.AppConfigProvider;
import org.example.models.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.utils.CustomAllureListener;

import static io.restassured.RestAssured.given;

public class RestBookerClient {

    static {
        RestAssured.baseURI = AppConfigProvider.config.baseUrl();
        RestAssured.filters(CustomAllureListener.withCustomTemplates(), new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Step("Create auth token with username: {request.username}")
    public RestResponse<TokenResponse> createToken(AuthRequest request) {
        return new RestResponse<>(
                given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .post("/auth")
                        .then(), TokenResponse.class);
    }

    @Step("Get all booking IDs")
    public RestResponse<BookingIds> getBookings() {
        return new RestResponse<>(
                given()
                        .get("/booking")
                        .then(), BookingIds.class);
    }

    @Step("Get booking by ID: {id}")
    public RestResponse<Booking> getBookingById(int id) {
        return new RestResponse<>(
                given()
                        .get("/booking/{id}", id).then(), Booking.class);
    }

    @Step("Create booking for guest: {body.firstname} {body.lastname}")
    public RestResponse<CreatedBookingResponse> createBooking(Booking body) {
        return new RestResponse<>(
                given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .post("/booking")
                        .then(), CreatedBookingResponse.class);
    }

    @Step("Update booking ID {id} with token {token}")
    public RestResponse<Booking> updateBooking(int id, String token, Booking body) {
        return new RestResponse<>(
                given()
                        .contentType(ContentType.JSON)
                        .header("Cookie", "token=" + token)
                        .body(body)
                        .put("/booking/{id}", id).then(), Booking.class);
    }

    @Step("Partial update booking ID {id} with token {token}")
    public RestResponse<Booking> partialUpdateBooking(int id, String token, Booking body) {
        return new RestResponse<>(
                given()
                        .contentType(ContentType.JSON)
                        .header("Cookie", "token=" + token)
                        .body(body)
                        .patch("/booking/" + id).then(), Booking.class);
    }

    @Step("Delete booking ID {id} with token {token}")
    public RestResponse<Void> deleteBooking(int id, String token) {
        return new RestResponse<>(given()
                .header("Cookie", "token=" + token)
                .delete("/booking/" + id).then(), Void.class);
    }
}