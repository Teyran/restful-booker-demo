package org.example.tests;

import io.qameta.allure.Description;

import org.example.config.AppConfigProvider;
import org.example.models.AuthRequest;
import org.example.models.TokenResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.example.data.BookingConstants.AUTH_BAD_CREDENTIALS_REASON;

public class AuthTests extends BaseTest {

    @Test
    @Description("Verify token creation using valid credentials")
    public void testCreateTokenValid() {
        AuthRequest authRequest = new AuthRequest(AppConfigProvider.config.username(), AppConfigProvider.config.password());

        TokenResponse token = client.createToken(authRequest)
                .checkStatusCode(HTTP_OK)
                .asObject();

        Assert.assertNotNull(token);
    }

    @Test
    @Description("Verify token creation using invalid credentials - returns 'Bad credentials'")
    public void testCreateTokenInvalid() {
        AuthRequest authRequest = bookingData.createInvalidAuthRequest();

        TokenResponse token = client.createToken(authRequest)
                .checkStatusCode(HTTP_OK)
                .asObject();

        Assert.assertEquals(token.getReason(), AUTH_BAD_CREDENTIALS_REASON);
    }
}
