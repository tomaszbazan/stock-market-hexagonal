package pl.btsoftware.market.common;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public record Price(BigDecimal value) {
    public Price {
        if (value.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("MaxPrice can't be less than zero.");
        }
    }
}
