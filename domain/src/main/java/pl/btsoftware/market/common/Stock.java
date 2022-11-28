package pl.btsoftware.market.common;

import pl.btsoftware.users.UserId;

public record Stock(UserId user, StockId stockId, Price price, Quantity quantity) {
}
