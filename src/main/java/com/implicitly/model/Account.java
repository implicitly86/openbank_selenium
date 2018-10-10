package com.implicitly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Emil Murzakaev.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Account {

    private Long id;

    private String number;

    private Double balance;

    @Override
    public String toString() {
        return number;
    }

}
