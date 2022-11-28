package pl.btsoftware.market.common;

public record Quantity(int value) {
    public Quantity {
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity can't be less or equals zero.");
        }
    }
}
