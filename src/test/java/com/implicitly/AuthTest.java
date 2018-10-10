/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly;

import com.implicitly.configuration.StandConfig;
import com.implicitly.model.User;
import com.implicitly.page.LoginPage;
import com.implicitly.util.DriverFactory;
import com.implicitly.util.PageStore;
import java.util.Iterator;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

/**
 * Тестовый класс, проверяющий авторизацию.
 *
 * @author Emil Murzakaev.
 */
public class AuthTest extends TestBase {

    /**
     * Тест авторизации.
     */
    @Features({"Авторизация"})
    @Stories({"Авторизация в системе"})
    @Title("Авторизация в системе")
    @Test(dataProvider = "userDP")
    public void testLogin(User user) {
        initBrowser();
        LoginPage loginPage = PageStore.getLocal().getLoginPage();
        loginPage.login(user);
        DriverFactory.closeDriver();
    }

    /**
     * Датапровайдер для теста {@link #testLogin(User)}
     */
    @DataProvider
    private Iterator<Object[]> userDP() {
        return StandConfig.getUsers()
                .stream()
                .map(it -> new Object[]{it})
                .collect(Collectors.toList())
                .iterator();
    }

}
