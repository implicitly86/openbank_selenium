package com.implicitly.util;

import org.openqa.selenium.WebDriver;

/**
 * @author Emil Murzakaev.
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new RuntimeException("Driver not registered!");
        }
        return driver.get();
    }

    public static void registerDriver(WebDriver driver) {
        DriverFactory.driver.set(driver);
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            driver.get().close();
            driver.remove();
        }
    }

}
