/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.util;

import com.implicitly.page.LoginPage;
import com.implicitly.page.MainPage;
import lombok.Getter;

/**
 * Хранилище PageStore.
 *
 * @author Emil Murzakaev.
 */
public class PageStore {

    /**
     * Хранилище наборов пейдж-обджектов для потока.
     */
    private static ThreadLocal<InnerStore> localStore = new ThreadLocal<>();

    /**
     * @return Возвращает набор пейдж-обджектов для конкретного потока.
     */
    public static InnerStore getLocal() {
        if (localStore.get() == null) {
            localStore.set(new InnerStore());
        }
        return localStore.get();
    }

    /**
     * Класс-хранилище экземпляров Page Objects для потока.
     */
    @Getter
    public static class InnerStore {
        private LoginPage loginPage = new LoginPage();
        private MainPage mainPage = new MainPage();
    }

}
