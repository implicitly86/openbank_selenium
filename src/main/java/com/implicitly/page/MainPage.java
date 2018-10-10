package com.implicitly.page;

import com.implicitly.model.Account;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.val;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * @author Emil Murzakaev.
 */
public class MainPage extends WebPage {

    private static final String ACCOUNT_COUNT_XPATH = "//tbody[@class='ant-table-tbody']/tr";
    private static final String ACCOUNT_ID_XPATH = "(//tbody[@class='ant-table-tbody']/tr)[%s]/td[1]";
    private static final String ACCOUNT_NUMBER_XPATH = "(//tbody[@class='ant-table-tbody']/tr)[%s]/td[2]";
    private static final String ACCOUNT_BALANCE_XPATH = "(//tbody[@class='ant-table-tbody']/tr)[%s]/td[3]";
    private static final String ACCOUNT_BALANCE_XPATH1 = "//tbody[@class='ant-table-tbody']/descendant::td[text()='%S']/../td[3]";
    private static final String BUTTON = "//button[descendant::span[text()='%s']]";
    private static final String INPUT = "//input[@placeholder='%s']";
    private static final String SELECT_OPEN = "//div[contains(@class,'ant-select ')]";
    private static final String SELECT_OPTION = "//li[contains(text(),'%s')]";
    private static final String DELETE_ACCOUNT = "(//td[text()='%s']/./following::button)[1]";
    private static final String LOGOUT_XPATH = "//li[text()='Выход']";

    @Step("Получение списка счетов")
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>(Collections.emptyList());
        val count = findElements(ACCOUNT_COUNT_XPATH).size();
        for (int i = 1; i <= count; i++) {
            val id = findElement(String.format(ACCOUNT_ID_XPATH, i)).getText();
            val number = findElement(String.format(ACCOUNT_NUMBER_XPATH, i)).getText();
            val balance = findElement(String.format(ACCOUNT_BALANCE_XPATH, i)).getText();
            accounts.add(new Account(Long.parseLong(id), number, Double.parseDouble(balance)));
        }
        return accounts;
    }

    @Step("Добавление нового счета {0}")
    public void addAccount(String number) {
        click(String.format(BUTTON, "Добавить счет"));
        input(String.format(INPUT, "Номер карты"), number);
        click(String.format(BUTTON, "Добавить счет"));
    }

    @Step("Получение баланса счета {0}")
    public Double getBalance(String number) {
        val balance = findElement(String.format(ACCOUNT_BALANCE_XPATH1, number)).getText();
        return Double.valueOf(balance);
    }

    @Step("Зачисление средств ({1}) на счет {0}")
    public void addAmount(Account account, Double amount) {
        click(String.format(BUTTON, "Зачислить на счет"));
        click(SELECT_OPEN);
        click(String.format(SELECT_OPTION, account.getNumber()));
        input(String.format(INPUT, "Сумма"), amount.toString());
        click(String.format(BUTTON, "Зачислить на счет"));
    }

    @Step("Списание средств ({1}) со счета {0}")
    public void subtractAmount(Account account, Double amount) {
        click(String.format(BUTTON, "Вычесть со счета"));
        click(SELECT_OPEN);
        click(String.format(SELECT_OPTION, account.getNumber()));
        input(String.format(INPUT, "Сумма"), amount.toString());
        click(String.format(BUTTON, "Вычесть со счета"));
    }

    @Step("Удаление счета {0}")
    public void deleteAccount(Account account) {
        click(String.format(DELETE_ACCOUNT, account.getNumber()));
    }

    @Step("Выход из системы")
    public void logout() {
        click(LOGOUT_XPATH);
    }

}
