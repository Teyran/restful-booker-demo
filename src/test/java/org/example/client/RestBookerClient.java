package org.example.client;

import io.qameta.allure.Step;
import org.example.models.*;

import static org.example.specs.BookingSpecFactory.*;
import static org.example.data.BookingEndpoints.*;

public class RestBookerClient {

    @Step("Create auth token with username: {request.username}")
    public RestResponse<TokenResponse> createToken(AuthRequest request) {
        return new RestResponse<>(
                baseRequest()
                        .body(request)
                        .post(AUTH)
                        .then(), TokenResponse.class);
    }

    @Step("Get all booking IDs")
    public RestResponse<BookingIds> getBookings() {
        return new RestResponse<>(
                baseRequest()
                        .get(BOOKING)
                        .then(), BookingIds.class);
    }

    @Step("Get booking by ID: {id}")
    public RestResponse<Booking> getBookingById(int id) {
        return new RestResponse<>(
                baseRequest()
                        .get(BOOKING_BY_ID, id).then(), Booking.class);
    }

    @Step("Create booking for guest: {body.firstname} {body.lastname}")
    public RestResponse<CreatedBookingResponse> createBooking(Booking body) {
        return new RestResponse<>(
                baseRequest()
                        .body(body)
                        .post(BOOKING)
                        .then(), CreatedBookingResponse.class);
    }

    @Step("Update booking ID {id} with token")
    public RestResponse<Booking> updateBooking(int id, String token, Booking body) {
        return new RestResponse<>(
                baseRequestWithToken(token)
                        .body(body)
                        .put(BOOKING_BY_ID, id).then(), Booking.class);
    }

    @Step("Partial update booking ID {id} with token")
    public RestResponse<Booking> partialUpdateBooking(int id, String token, Booking body) {
        return new RestResponse<>(
                baseRequestWithToken(token)
                        .body(body)
                        .patch(BOOKING_BY_ID, id).then(), Booking.class);
    }

    @Step("Delete booking ID {id} with token")
    public RestResponse<Void> deleteBooking(int id, String token) {
        return new RestResponse<>(baseRequestWithToken(token)
                .delete(BOOKING_BY_ID, id).then(), Void.class);
    }
}