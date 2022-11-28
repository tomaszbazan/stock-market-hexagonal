package pl.btsoftware.market;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.btsoftware.market.common.*;
import pl.btsoftware.users.UserId;
import pl.btsoftware.users.VerifyUser;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockBuyCommandResolverTest {
    @InjectMocks
    StockBuyCommandResolver stockBuyCommand;

    @Mock
    VerifyUser verifyUser;

    @Mock
    UserStockRepository userStockRepository;


    @Test
    void shouldThrowExceptionWhenUserIsNotValid() {
        // given
        UserId userId = new UserId(123);
        givenUserIsNotVerified(userId);

        // when & then
        assertThrows(UserNotValidException.class, () -> stockBuyCommand.handle(userId, stockBuyRequest()));
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoStockToBuy() {
        // given
        UserId userId = new UserId(123);
        StockBuyRequest buyRequest = stockBuyRequest();
        givenUserIsVerified(userId);
        givenStockDoesNotExists(buyRequest);

        // when & then
        assertThrows(NothingToBuyException.class, () -> stockBuyCommand.handle(userId, stockBuyRequest()));
    }

    @Test
    void shouldMakeTransactionWhenStockToBuyExists() {
        // given
        UserId userId = new UserId(123);
        StockBuyRequest buyRequest = stockBuyRequest();
        givenUserIsVerified(userId);
        givenStockExistsAndIsToBuy(buyRequest);

        // when
        stockBuyCommand.handle(userId, stockBuyRequest());

        // then
        userStockRepository.addStockToUser(userId, buyRequest);
        userStockRepository.removeStockFromUser(any(), any());
    }

    private void givenStockExistsAndIsToBuy(StockBuyRequest buyRequest) {
        when(userStockRepository.find(buyRequest)).thenReturn(Optional.of(new Stock(new UserId(111), buyRequest.stockId(), buyRequest.maxPrice(), buyRequest.quantity())));
    }

    private void givenStockDoesNotExists(StockBuyRequest buyRequest) {
        when(userStockRepository.find(buyRequest)).thenReturn(Optional.empty());
    }

    private void givenUserIsVerified(UserId userId) {
        when(verifyUser.isValid(userId)).thenReturn(true);
    }

    private void givenUserIsNotVerified(UserId userId) {
        when(verifyUser.isValid(userId)).thenReturn(false);
    }

    private StockBuyRequest stockBuyRequest() {
        return new StockBuyRequest(new StockId(111), new Price(new BigDecimal("111")), new Quantity(3));
    }

}
