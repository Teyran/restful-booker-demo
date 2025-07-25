package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDates {
    @JsonProperty("checkin")
    private String checkIn;

    @JsonProperty("checkout")
    private String checkout;
}