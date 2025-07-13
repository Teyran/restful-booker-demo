package org.example.tests;

import io.qameta.allure.Description;

import org.example.config.AppConfigProvider;
import org.example.models.AuthRequest;
import org.example.models.TokenResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.net.HttpURLConnection.HTTP_OK;

public class AuthTests extends BaseTest {

    @Test
    @Description("Проверка создания токена с валидными credentials")
    public void testCreateTokenValid() {
        AuthRequest authRequest = new AuthRequest(AppConfigProvider.config.username(), AppConfigProvider.config.password());

        TokenResponse token = client.createToken(authRequest)
                .checkStatusCode(HTTP_OK)
                .asObject();

        Assert.assertNotNull(token);
    }

    @Test
    @Description("Проверка создания токена с невалидными credentials")
    public void testCreateTokenInvalid() {
        AuthRequest authRequest = new AuthRequest("invalid", "creds");

        TokenResponse token = client.createToken(authRequest)
                .checkStatusCode(HTTP_OK)
                .asObject();

        Assert.assertEquals(token.getReason(), "Bad credentials");
    }
}
