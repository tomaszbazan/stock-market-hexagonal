package pl.btsoftware.market;

import pl.btsoftware.market.common.Stock;
import pl.btsoftware.market.common.StockBuyRequest;
import pl.btsoftware.users.UserId;
import pl.btsoftware.users.VerifyUser;

class StockBuyCommandResolver implements StockBuyCommand {
    private final VerifyUser verifyUser;
    private final UserStockRepository userStockRepository;

    public StockBuyCommandResolver(VerifyUser verifyUser, UserStockRepository userStockRepository) {
        this.verifyUser = verifyUser;
        this.userStockRepository = userStockRepository;
    }

    @Override
    public void handle(UserId user, StockBuyRequest request) throws UserNotValidException, NothingToBuyException {
        if(!verifyUser.isValid(user)) {
            throw new UserNotValidException();
        }

        Stock stock = userStockRepository.find(request).orElseThrow(NothingToBuyException::new);

        userStockRepository.addStockToUser(user, request);
        userStockRepository.removeStockFromUser(stock.user(), stock.stockId());
    }
}
