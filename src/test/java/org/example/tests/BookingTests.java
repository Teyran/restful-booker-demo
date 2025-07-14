package org.example.tests;

import io.qameta.allure.Description;
import org.example.models.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.example.data.BookingConstants.*;
import static org.example.utils.CustomAssertions.assertAll;

public class BookingTests extends BaseTest {

    @Test(groups = {"smoke"})
    @Description("Create a new booking")
    public void testCreateBooking() {
        Booking body = bookingData.createRandomBooking();

        CreatedBookingResponse response = client.createBooking(body).asObjectWithStatus(HTTP_OK);

        Assert.assertNotNull(response.getBookingId());
        Assert.assertEquals(response.getBooking().getFirstName(), body.getFirstName());
    }

    @Test(groups = {"regression"})
    @Description("Retrieve all bookings")
    public void testGetBookingIDs() {
        List<BookingIds> list = client.getBookings().checkStatusCode(HTTP_OK).asList();

        Assert.assertNotNull(list);
    }

    @Test(groups = {"smoke"})
    @Description("Retrieve a booking by ID")
    public void testGetBookingById() {
        List<BookingIds> list = client.getBookings().checkStatusCode(HTTP_OK).asList();
        int randomId = list.get(0).getBookingId();

        Booking response = client.getBookingById(randomId).asObjectWithStatus(HTTP_OK);

        Assert.assertNotNull(response);
    }

    @Test(groups = {"smoke"}, dataProvider = "tokenProvider", dataProviderClass = BaseTest.class)
    @Description("Verify full booking update with authorization")
    public void testUpdateBooking(String token) {
        Booking initialBooking = bookingData.createInitialBooking();
        int bookingId = client.createBooking(initialBooking).asObjectWithStatus(HTTP_OK).getBookingId();
        Booking updatedBooking = bookingData.createUpdatedBooking();

        client.updateBooking(bookingId, token, updatedBooking).checkStatusCode(HTTP_OK);
        Booking getResponse = client.getBookingById(bookingId).asObjectWithStatus(HTTP_OK);

        assertAll(softly -> {
            softly.assertEquals(getResponse.getFirstName(), updatedBooking.getFirstName(), "First name was not updated");
            softly.assertEquals(getResponse.getTotalPrice(), updatedBooking.getTotalPrice(), "Total price was not updated");
        });
    }

    @Test(groups = {"regression"})
    @Description("Verify updating booking without authorization returns 403")
    public void testUpdateBookingUnauthorized() {
        Booking initialBooking = bookingData.createInitialBooking();
        int bookingId = client.createBooking(initialBooking).asObjectWithStatus(HTTP_OK).getBookingId();
        Booking updatedBooking = bookingData.createUpdatedBooking();

        client.updateBooking(bookingId, INVALID_TOKEN, updatedBooking).checkStatusCode(HTTP_FORBIDDEN);
    }

    @Test(groups = {"smoke"}, dataProvider = "tokenProvider", dataProviderClass = BaseTest.class)
    @Description("Verify partial booking update with authorization")
    public void testPartialUpdateBooking(String token) {
        Booking initialBooking = bookingData.createInitialBooking();
        int bookingId = client.createBooking(initialBooking).asObjectWithStatus(HTTP_OK).getBookingId();

        Booking partialUpdate = new Booking();
        partialUpdate.setFirstName(FIRST_NAME_JANE);
        partialUpdate.setTotalPrice(TOTAL_PRICE);

        client.partialUpdateBooking(bookingId, token, partialUpdate).checkStatusCode(HTTP_OK);
        Booking getResponse = client.getBookingById(bookingId).asObjectWithStatus(HTTP_OK);

        assertAll(softly -> {
            softly.assertEquals(getResponse.getFirstName(), FIRST_NAME_JANE, "First name was not changed");
            softly.assertEquals(getResponse.getTotalPrice(), TOTAL_PRICE, "Total price was not updated");
            softly.assertEquals(getResponse.getLastName(), initialBooking.getLastName(), "Last name should not be changed");
        });
    }

    @Test(groups = {"regression"})
    @Description("Verify partial booking update without authorization returns 403")
    public void testPartialUpdateBookingUnauthorized() {
        Booking initialBooking = bookingData.createInitialBooking();
        int bookingId = client.createBooking(initialBooking).asObjectWithStatus(HTTP_OK).getBookingId();

        Booking partialUpdate = new Booking();
        partialUpdate.setFirstName(FIRST_NAME_JANE);

        client.partialUpdateBooking(bookingId, INVALID_TOKEN, partialUpdate).checkStatusCode(HTTP_FORBIDDEN);
    }

    @Test(groups = {"defect"}, dataProvider = "tokenProvider", dataProviderClass = BaseTest.class)
    @Description("Verify booking deletion with authorization")
    public void testDeleteBooking(String token) {
        Booking initialBooking = bookingData.createInitialBooking();
        int bookingId  = client.createBooking(initialBooking).asObjectWithStatus(HTTP_OK).getBookingId();

        client.deleteBooking(bookingId, token).checkStatusCode(HTTP_NO_CONTENT);
        client.getBookingById(bookingId).checkStatusCode(HTTP_NOT_FOUND);
    }

    @Test(groups = {"regression"})
    @Description("Verify booking deletion without authorization returns 403")
    public void testDeleteBookingUnauthorized() {
        Booking initialBooking = bookingData.createInitialBooking();
        int bookingId = client.createBooking(initialBooking).asObjectWithStatus(HTTP_OK).getBookingId();

        client.deleteBooking(bookingId, INVALID_TOKEN).checkStatusCode(HTTP_FORBIDDEN);
    }
}
