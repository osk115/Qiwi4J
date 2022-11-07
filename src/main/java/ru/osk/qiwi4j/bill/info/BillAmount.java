package ru.osk.qiwi4j.bill.info;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillAmount {

    BigDecimal value;
    Currency currency;

    public BillAmount(BigDecimal value, Currency currency) {
        this.value = value.setScale(2, RoundingMode.HALF_DOWN);
        this.currency = currency;
    }

    public enum Currency {
        RUB,
        KZT
    }

}
