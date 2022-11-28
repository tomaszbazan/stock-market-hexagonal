package pl.btsoftware.market;

import pl.btsoftware.DomainEvent;
import pl.btsoftware.market.common.StockBuyRequest;

import java.math.BigDecimal;

public record StockBoughtV1(String type, int quantity, BigDecimal price) implements DomainEvent {
    static StockBoughtV1 from(StockBuyRequest request) {
        return new StockBoughtV1("StockBought", request.quantity().value(), request.maxPrice().value());
    }
}
