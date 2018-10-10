/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.page;

import com.implicitly.model.User;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * PageObject, отвечающий за страницу входа в систему.
 *
 * @author Emil Murzakaev.
 */
public class LoginPage extends WebPage {

    private static final String LOGIN_XPATH = "//input[@id='login']";
    private static final String PASSWORD_XPATH = "//input[@id='password']";
    private static final String BUTTON_XPATH = "//button[descendant::span[text()='Вход']]";

    /**
     * Вход в систему.
     *
     * @param user {@link User}
     */
    @Step("Авторизация в системе под пользователем {0}")
    public void login(User user) {
        input(LOGIN_XPATH, user.getLogin());
        input(PASSWORD_XPATH, user.getPassword());
        click(BUTTON_XPATH);
    }

}
