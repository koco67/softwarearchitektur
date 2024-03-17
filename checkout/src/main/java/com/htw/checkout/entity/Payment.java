package com.htw.checkout.entity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Payment {
    private String ZIP;
    private String Street;
    private String City;
    private String Country;
    private String IBAN;

}
