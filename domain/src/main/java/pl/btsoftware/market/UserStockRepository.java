package pl.btsoftware.market;

import pl.btsoftware.market.common.*;
import pl.btsoftware.users.UserId;

import java.util.Optional;

public interface UserStockRepository {
    Optional<Stock> find(StockBuyRequest buyRequest);
    void removeStockFromUser(UserId userId, StockId stock);
    void addStockToUser(UserId user, StockBuyRequest buyRequest);
}
