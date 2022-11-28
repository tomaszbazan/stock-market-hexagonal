package pl.btsoftware.market;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.btsoftware.tools.IntegrationTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.btsoftware.market.MarketRestController.*;

@IntegrationTest
class BuyStockIT {

    @Autowired
    MarketRestController restController;

    @Autowired
    TransactionEventsJpaRepository eventsRepository;

    @Autowired
    UserStockJpaRepository userStockRepository;

    @BeforeEach
    void setUp() {
        userStockRepository.deleteAll();
        eventsRepository.deleteAll();
    }

    @Test
    void shouldTransferStockFromOneUserToAnther() {
        // given
        var stockId = 123;
        var userWhoOwnsStock = 555;
        var userWhoBuyStock = 666;
        stockWithGivenIdExists(userWhoOwnsStock, stockId);

        // when
        restController.buyStock(stockId, new BuyRequest(userWhoBuyStock, new BigDecimal("15"), 3));

        // then
        assertThat(userStockRepository.findById(new UserStockId(userWhoOwnsStock, stockId))).isEmpty();
        assertThat(userStockRepository.findById(new UserStockId(userWhoBuyStock, stockId))).isNotEmpty();
    }

    @Test
    void shouldStoreEventForEveryTransaction() {
        // given
        var stockId = 123;
        var userWhoOwnsStock = 555;
        var userWhoBuyStock = 666;
        stockWithGivenIdExists(userWhoOwnsStock, stockId);

        // when
        restController.buyStock(stockId, new BuyRequest(userWhoBuyStock, new BigDecimal("15"), 3));

        // then
        assertThat(eventsRepository.findAll()).hasSize(2);
    }

    private void stockWithGivenIdExists(int userId, int stockId) {
        userStockRepository.save(new UserStock(new UserStockId(userId, stockId), 3, new BigDecimal("12.12"), true));
    }

}
