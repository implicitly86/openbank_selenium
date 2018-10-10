/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель сущности <strong>Пользователь</strong>
 *
 * @author Emil Murzakaev.
 */
@Data
@Builder
@EqualsAndHashCode
public class User {

    /**
     * Имя пользователя.
     */
    private String login;
    /**
     * Пароль.
     */
    private String password;

    private List<Account> accounts;

}
