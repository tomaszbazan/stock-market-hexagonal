package pl.btsoftware.market;

import pl.btsoftware.market.common.StockBuyRequest;
import pl.btsoftware.users.UserId;

@FunctionalInterface
public interface StockBuyCommand {
    void handle(UserId user, StockBuyRequest request) throws UserNotValidException, NothingToBuyException;
}
