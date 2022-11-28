package pl.btsoftware.market.common;

public record StockBuyRequest(StockId stockId, Price maxPrice, Quantity quantity) {
}

