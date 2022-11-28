package pl.btsoftware.market;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.btsoftware.market.common.Price;
import pl.btsoftware.market.common.Quantity;
import pl.btsoftware.market.common.StockBuyRequest;
import pl.btsoftware.market.common.StockId;
import pl.btsoftware.users.UserId;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
class MarketRestController {
    private final StockBuyCommand buyCommand;

    @PostMapping("/v1/stock/{stockId}/buy")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Transactional
    void buyStock(@PathVariable int stockId, @RequestBody @Valid BuyRequest request) {
        buyCommand.handle(new UserId(request.userId), new StockBuyRequest(new StockId(stockId), new Price(request.maxPrice), new Quantity(request.quantity)));
    }

    record BuyRequest(int userId, @NumberFormat(style = NumberFormat.Style.CURRENCY) @Min(value = 0) BigDecimal maxPrice, @Min(value = 1) int quantity) {
    }

    @ExceptionHandler(UserNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    void handleIllegalArgument() {
        log.debug("Incorrect user");
    }
}
