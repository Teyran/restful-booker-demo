package org.example.tests;

import io.qameta.allure.Description;
import org.example.data.BookingData;
import org.example.models.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BookingTests extends BaseTest {
    private final BookingData bookingData = new BookingData();

    @Description("Create a new booking")
    @Test
    public void testCreateBooking() {
        Booking body = bookingData.createRandomBooking();

        CreatedBookingResponse response = client.createBooking(body)
                .checkStatusCode(200)
                .asObject();

        Assert.assertNotNull(response.getBookingId());
        Assert.assertEquals(response.getBooking().getFirstName(), body.getFirstName());
    }

    @Description("Retrieve all bookings")
    @Test
    public void testGetBookingIDs(){
        List<BookingIds> list = client.getBookings()
                .checkStatusCode(200)
                .asList();

        Assert.assertNotNull(list);
    }

    @Test
    @Description("Retrieve a booking by ID")
    public void testGetBookingById(){
        List<BookingIds> list = client.getBookings()
                .checkStatusCode(200)
                .asList();

        int randomId = list.get(0).getBookingId();

        Booking response = client.getBookingById(randomId)
                .checkStatusCode(200).asObject();

        Assert.assertNotNull(response);
    }


    @Test(dataProvider = "tokenProvider",dataProviderClass = BaseTest.class)
    @Description("Verify full booking update with authorization")
    public void testUpdateBooking(String token) {
        Booking initialBooking = bookingData.createInitialBooking();

        CreatedBookingResponse createResponse = client.createBooking(initialBooking)
                .checkStatusCode(200)
                .asObject();

        int bookingId = createResponse.getBookingId();

        Booking updatedBooking = bookingData.createUpdatedBooking();

        client.updateBooking(bookingId, token, updatedBooking)
                .checkStatusCode(200);

        Booking getResponse = client.getBookingById(bookingId)
                .checkStatusCode(200)
                .asObject();

        Assert.assertEquals(getResponse.getFirstName(), updatedBooking.getFirstName(), "Имя не обновилось");
        Assert.assertEquals(getResponse.getTotalPrice(), updatedBooking.getTotalPrice(), "Цена не обновилась");
    }

    @Test
    @Description("Verify updating booking without authorization returns 403")
    public void testUpdateBookingUnauthorized() {
        Booking initialBooking = bookingData.createInitialBooking();

        CreatedBookingResponse createResponse = client.createBooking(initialBooking)
                .checkStatusCode(200)
                .asObject();

        int bookingId = createResponse.getBookingId();

        Booking updatedBooking = bookingData.createUpdatedBooking();
        client.updateBooking(bookingId, "invalid_token", updatedBooking).checkStatusCode(403);
    }

    @Test(dataProvider = "tokenProvider",dataProviderClass = BaseTest.class)
    @Description("Verify partial booking update with authorization")
    public void testPartialUpdateBooking(String token) {
        Booking initialBooking = bookingData.createInitialBooking();

        CreatedBookingResponse createResponse = client.createBooking(initialBooking)
                .checkStatusCode(200)
                .asObject();

        int bookingId = createResponse.getBookingId();

        Booking partialUpdate = new Booking();
        partialUpdate.setFirstName("Jane");
        partialUpdate.setTotalPrice(200);
        client.partialUpdateBooking(bookingId, token, partialUpdate).checkStatusCode(200);

        Booking getResponse = client.getBookingById(bookingId)
                .checkStatusCode(200)
                .asObject();

        Assert.assertEquals(getResponse.getFirstName(), "Jane", "Имя не обновилось");
        Assert.assertEquals(getResponse.getTotalPrice(), 200, "Цена не обновилась");
        Assert.assertEquals(getResponse.getLastName(), initialBooking.getLastName(), "Фамилия должна остаться без изменений");
    }

    @Test
    @Description("Verify partial booking update without authorization returns 403")
    public void testPartialUpdateBookingUnauthorized() {
        Booking initialBooking = bookingData.createInitialBooking();

        CreatedBookingResponse createResponse = client.createBooking(initialBooking)
                .checkStatusCode(200)
                .asObject();

        int bookingId = createResponse.getBookingId();

        Booking partialUpdate = new Booking();
        partialUpdate.setFirstName("Jane");

        client.partialUpdateBooking(bookingId, "invalid_token", partialUpdate)
                .checkStatusCode(403);
    }

    @Test(dataProvider = "tokenProvider",dataProviderClass = BaseTest.class)
    @Description("Verify booking deletion with authorization")
    public void testDeleteBooking(String token) {
        Booking initialBooking = bookingData.createInitialBooking();

        CreatedBookingResponse createResponse = client.createBooking(initialBooking)
                .checkStatusCode(200)
                .asObject();

        int bookingId = createResponse.getBookingId();

        client.deleteBooking(bookingId, token).checkStatusCode(201);

        client.getBookingById(bookingId).checkStatusCode(404);
    }

    @Test
    @Description("Verify booking deletion without authorization returns 403")
    public void testDeleteBookingUnauthorized() {
        Booking initialBooking = bookingData.createInitialBooking();

        CreatedBookingResponse createResponse = client.createBooking(initialBooking)
                .checkStatusCode(200)
                .asObject();

        int bookingId = createResponse.getBookingId();

        client.deleteBooking(bookingId, "invalid_token").checkStatusCode(403);
    }
}
