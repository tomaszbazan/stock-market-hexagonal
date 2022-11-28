package pl.btsoftware.market;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.btsoftware.market.common.*;
import pl.btsoftware.users.UserId;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class TransactionJpaRepository implements UserStockRepository {

    private final TransactionEventsJpaRepository eventsRepository;
    private final UserStockJpaRepository userStockRepository;

    @Override
    public Optional<Stock> find(StockBuyRequest buyRequest) {
        return userStockRepository.findFirstByForSoldIsTrueAndIdStockIdEqualsAndAndQuantityEqualsAndAndPriceIsLessThan(buyRequest.stockId().value(), buyRequest.quantity().value(), buyRequest.maxPrice().value())
                .map(s -> new Stock(s.getUserId(), s.getStockId(), new Price(s.getPrice()), new Quantity(s.getQuantity())));
    }

    @Override
    @Transactional
    public void removeStockFromUser(UserId userId, StockId stockId) {
        userStockRepository.findById(new UserStockId(userId, stockId)).ifPresent(stock -> {
            eventsRepository.save(UserTransactionEvent.soldRequest(userId, stock));
            userStockRepository.delete(stock);
        });
    }

    @Override
    @Transactional
    public void addStockToUser(UserId user, StockBuyRequest buyRequest) {
        eventsRepository.save(UserTransactionEvent.buyRequest(user, buyRequest));
        userStockRepository.save(UserStock.from(user, buyRequest));
    }
}
