/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly;

import com.implicitly.configuration.StandConfig;
import com.implicitly.model.Account;
import com.implicitly.model.User;
import com.implicitly.page.LoginPage;
import com.implicitly.page.MainPage;
import com.implicitly.util.DriverFactory;
import com.implicitly.util.PageStore;
import java.util.Iterator;
import java.util.stream.Collectors;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

/**
 * @author Emil Murzakaev.
 */
public class AccountTest extends TestBase {

    @Features({"Счета"})
    @Stories({"Получение пользовательских счетов"})
    @Title("Получение пользовательских счетов")
    @Test(dataProvider = "userDP")
    public void testGetAccounts(User user, String accountNumber) {
        initBrowser();
        LoginPage loginPage = PageStore.getLocal().getLoginPage();
        loginPage.login(user);
        MainPage mainPage = PageStore.getLocal().getMainPage();
        user.setAccounts(mainPage.getAccounts());
        mainPage.logout();
        DriverFactory.closeDriver();
    }

    @Features({"Счета"})
    @Stories({"Добавление нового счета"})
    @Title("Добавление нового счета")
    @Test(dataProvider = "userDP")
    public void testAddAccount(User user, String accountNumber) throws Exception {
        initBrowser();
        LoginPage loginPage = PageStore.getLocal().getLoginPage();
        loginPage.login(user);
        MainPage mainPage = PageStore.getLocal().getMainPage();
        mainPage.addAccount(accountNumber);
        Thread.sleep(1000);
        Assert.assertEquals(mainPage.getBalance(accountNumber), 0.0);
        user.setAccounts(mainPage.getAccounts());
        mainPage.logout();
        DriverFactory.closeDriver();
    }

    @Features({"Счета"})
    @Stories({"Зачисление средств на счет"})
    @Title("Зачисление средств на счет")
    @Test(dataProvider = "userDP")
    public void testAddAmount(User user, String accountNumber) throws Exception {
        initBrowser();
        LoginPage loginPage = PageStore.getLocal().getLoginPage();
        loginPage.login(user);
        MainPage mainPage = PageStore.getLocal().getMainPage();
        Account account = user.getAccounts()
                .stream()
                .filter(it -> it.getNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account with number \"" + accountNumber + "\" not found"));
        mainPage.addAmount(account, 100.0);
        account.setBalance(account.getBalance() + 100.0);
        Thread.sleep(1000);
        Assert.assertEquals(mainPage.getBalance(accountNumber), account.getBalance());
        mainPage.logout();
        DriverFactory.closeDriver();
    }

    @Features({"Счета"})
    @Stories({"Списание средств со счета"})
    @Title("Списание средств со счета")
    @Test(dataProvider = "userDP")
    public void testSubtractAmount(User user, String accountNumber) throws Exception {
        initBrowser();
        LoginPage loginPage = PageStore.getLocal().getLoginPage();
        loginPage.login(user);
        MainPage mainPage = PageStore.getLocal().getMainPage();
        Account account = user.getAccounts()
                .stream()
                .filter(it -> it.getNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account with number \"" + accountNumber + "\" not found"));
        mainPage.subtractAmount(account, 50.0);
        account.setBalance(account.getBalance() - 50.0);
        Thread.sleep(1000);
        Assert.assertEquals(mainPage.getBalance(accountNumber), account.getBalance());
        mainPage.logout();
        DriverFactory.closeDriver();
    }

    @Features({"Счета"})
    @Stories({"Удаление счета"})
    @Title("Удаление счета")
    @Test(dataProvider = "userDP")
    public void testDeleteAccount(User user, String accountNumber) {
        initBrowser();
        LoginPage loginPage = PageStore.getLocal().getLoginPage();
        loginPage.login(user);
        MainPage mainPage = PageStore.getLocal().getMainPage();
        Account account = user.getAccounts()
                .stream()
                .filter(it -> it.getNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Account with number \"" + accountNumber + "\" not found"));
        mainPage.deleteAccount(account);
        mainPage.logout();
        DriverFactory.closeDriver();
    }

    /**
     * Датапровайдер для тестов
     */
    @DataProvider
    private Iterator<Object[]> userDP() {
        return StandConfig.getUsers()
                .stream()
                .limit(1)
                .map(it -> new Object[]{it, "42760000" + String.valueOf(System.currentTimeMillis()).substring(0, 7)})
                .collect(Collectors.toList())
                .iterator();
    }

}
