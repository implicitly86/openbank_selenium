/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.page;

import com.implicitly.util.DriverFactory;
import java.util.Collections;
import java.util.List;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Абстрактный класс веб страницы.
 *
 * @author Emil Murzakaev.
 */
abstract class WebPage {

    @Step("Поиск элементов по xpath : {0}")
    List<WebElement> findElements(String xpath) {
        List<WebElement> result = Collections.emptyList();
        for (int i=0; i < 10; i++) {
            try {
                result = DriverFactory.getDriver().findElements(By.xpath(xpath));
                break;
            } catch (WebDriverException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    // ignore
                }
                // retry find elements
            }
        }
        return result;
    }

    @Step("Поиск элемента по xpath : {0}")
    WebElement findElement(String xpath) {
        val elements = this.findElements(xpath);
        if (elements.isEmpty()) {
            throw new AssertionError("Element not found");
        }
        return elements.get(0);
    }

    @Step("Нажатие на элемент по xpath : {0}")
    void click(String xpath) {
        val element = findElement(xpath);
        element.click();
    }

    @Step("Ввод значения {1} в элемент по xpath : {0}")
    void input(String xpath, String value) {
        val element = findElement(xpath);
        element.sendKeys(value);
    }

    @Step("Выбор значения {1} в элементе по xpath : {0}")
    void select(String xpath, String value) {
        val element = findElement(xpath);
        new Select(element).selectByVisibleText(value);
    }

}
