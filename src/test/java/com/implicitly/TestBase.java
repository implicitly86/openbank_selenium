/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly;

import com.implicitly.configuration.StandConfig;
import com.implicitly.util.DriverFactory;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import ru.stqa.selenium.factory.WebDriverPool;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Базовый тестовый класс.
 *
 * @author Emil Murzakaev.
 */
public class TestBase {

    /**
     * {@link Logger}
     */
    private final static Logger logger = LoggerFactory.getLogger(TestBase.class);

    /*
     * Инициализация классов.
     */
    static {
        logger.info("Initialization...");
        final String pathToStandConfig = System.getProperty("stand.config");
        logger.info("Путь к конфигурационному файлу стенда: \"{}\"", pathToStandConfig);
        StandConfig.init(pathToStandConfig);
        logger.info("Initialization complete");
    }

    /**
     * Инициализация браузера.
     */
    @Step("Init browser")
    protected void initBrowser() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=ru", "--no-sandbox", "--incognito", "--disable-popup-blocking");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        String gridUrl = System.getProperty("grid.url");

        WebDriver driver;
        if (gridUrl == null || gridUrl.isEmpty()) {
            driver = WebDriverPool.DEFAULT.getDriver(capabilities);
        } else {
            try {
                driver = WebDriverPool.DEFAULT.getDriver(new URL(gridUrl), capabilities);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        try {
            Window window = driver.manage().window();
            driver.get("chrome://extensions-frame");
            WebElement checkbox = driver.findElement(By.xpath("//label[@class='incognito-control']/input[@type='checkbox']"));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            window.setPosition(new Point(0, 0));
            window.maximize();
        } catch (Exception ex) {
            // ignore
        }
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.get(StandConfig.getConfig().getString("login_page"));
        DriverFactory.registerDriver(driver);
    }

    /**
     * {@link BeforeSuite}
     */
    @BeforeSuite
    private void beforeSuite() {
        logger.info("beforeSuite");
    }

    /**
     * {@link BeforeTest}
     */
    @BeforeTest
    private void beforeTest() {
        logger.info("beforeTest");
    }

    /**
     * {@link BeforeMethod}
     */
    @BeforeMethod
    private void beforeMethod() {
        logger.info("beforeMethod");
    }

    /**
     * {@link AfterMethod}
     */
    @AfterMethod
    private void afterMethod() {
        DriverFactory.closeDriver();
        logger.info("afterMethod");
    }

    /**
     * {@link AfterTest}
     */
    @AfterTest
    private void afterTest() {
        logger.info("afterTest");
    }

    /**
     * {@link AfterSuite}
     */
    @AfterSuite
    private void afterSuite() {
        logger.info("afterSuite");
    }

}
