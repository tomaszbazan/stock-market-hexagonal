package pl.btsoftware.market;

import pl.btsoftware.DomainEvent;

import java.math.BigDecimal;

public record StockSoldV1(String type, int quantity, BigDecimal price) implements DomainEvent {
    static StockSoldV1 from(UserStock request) {
        return new StockSoldV1("StockSold", request.getQuantity(), request.getPrice());
    }
}
