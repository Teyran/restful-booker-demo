package org.example.data;

import com.github.javafaker.Faker;
import org.example.models.AuthRequest;
import org.example.models.Booking;
import org.example.models.BookingDates;

import static org.example.data.BookingConstants.TOTAL_PRICE;

public class BookingData {
    private final Faker faker = new Faker();

    public Booking createInitialBooking() {
        return new Booking(
                faker.funnyName().name(),
                faker.name().lastName(),
                faker.random().nextInt(1000),
                true,
                new BookingDates("2023-01-01", "2023-01-02"),
                faker.beer().name()
        );
    }

    public Booking createUpdatedBooking() {
        return new Booking(
                faker.name().name(),
                faker.name().lastName(),
                TOTAL_PRICE,
                false,
                new BookingDates("2025-02-01", "2023-02-02"),
                faker.beer().name()
        );
    }

    public Booking createRandomBooking(){
       return Booking.builder()
                .firstName(faker.name().username())
                .lastName(faker.animal().name())
                .totalPrice(faker.random().nextInt(500))
                .depositPaid(faker.random().nextBoolean())
                .additionalNeeds(faker.beer().name())
                .bookingDates(new BookingDates(faker.date().birthday().toString(), faker.date().birthday().toString()))
                .build();
    }

    public AuthRequest createInvalidAuthRequest() {
        return AuthRequest.builder()
                .username(faker.name().username())
                .password(String.valueOf(faker.number().randomNumber()))
                .build();
    }
}
