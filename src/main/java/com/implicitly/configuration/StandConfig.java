/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.configuration;

import com.implicitly.model.User;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Конфигурация стенда.
 *
 * @author Emil Murzakaev.
 */
public class StandConfig {

    /**
     * Приватный экземпляр класса.
     */
    private static Config config;
    /**
     * Пользователи стенда.
     */
    private static List<User> users;

    /**
     * Инициализация конфига стенда.
     *
     * @param pathToConfFile путь до конфига стенда.
     */
    public static void init(final String pathToConfFile) {
        final File configFile = new File(pathToConfFile);
        if (!configFile.exists()) {
            throw new RuntimeException("Конфигурационный файл \"" + pathToConfFile + "\" не найден!");
        }
        config = ConfigFactory.parseFile(configFile).resolve();
        users = config.getConfigList("users")
                .stream()
                .map(config -> User.builder()
                        .login(config.getString("login"))
                        .password(config.getString("password"))
                        .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * Получить конфиг.
     *
     * @return конфиг.
     */
    public static Config getConfig() {
        return config;
    }

    /**
     * Получить список пользователей стенда.
     *
     * @return список пользователей стенда
     */
    public static List<User> getUsers() {
        return users;
    }

}
