package org.example.tests;

import org.example.client.RestBookerClient;
import org.example.config.AppConfigProvider;
import org.example.data.BookingData;
import org.example.models.AuthRequest;
import org.example.models.TokenResponse;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class BaseTest {
    protected final RestBookerClient client = new RestBookerClient();
    protected final BookingData bookingData = new BookingData();

    @DataProvider(name = "tokenProvider")
    public Object[][] provideToken(ITestContext context) {
        AuthRequest authRequest = new AuthRequest(AppConfigProvider.config.username(), AppConfigProvider.config.password());

        TokenResponse response = client.createToken(authRequest)
                .checkStatusCode(200)
                .asObject();

        return new Object[][]{{response.getToken()}};
    }
}
