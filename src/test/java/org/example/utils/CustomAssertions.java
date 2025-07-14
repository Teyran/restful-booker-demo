package org.example.utils;

import org.testng.asserts.SoftAssert;

import java.util.function.Consumer;

public class CustomAssertions {
    public static void assertAll(Consumer<SoftAssert> assertions) {
        SoftAssert softly = new SoftAssert();
        assertions.accept(softly);
        softly.assertAll();
    }
}
