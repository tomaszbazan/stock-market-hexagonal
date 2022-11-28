package pl.btsoftware.market;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.btsoftware.market.common.Price;
import pl.btsoftware.market.common.Quantity;
import pl.btsoftware.market.common.StockBuyRequest;
import pl.btsoftware.market.common.StockId;
import pl.btsoftware.users.UserId;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarketRestController.class)
class MarketRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StockBuyCommand stockBuyCommand;

    @Test
    void shouldBuySelectedStock() throws Exception {
        // given
        String request = """
                {
                    "userId": 123,
                    "maxPrice": 12.12,
                    "quantity": 3
                }
                """;

        // when
        mockMvc.perform(post("/v1/stock/{id}/buy", 111)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isAccepted());

        // then
        verify(stockBuyCommand).handle(new UserId(123), new StockBuyRequest(new StockId(111), new Price(new BigDecimal("12.12")), new Quantity(3)));
    }

    @Test
    void shouldNotAllowToBuyWhenMaxPriceIsLessThan0() throws Exception {
        // given
        String request = """
                {
                    "userId": 123,
                    "maxPrice": -12.12,
                    "quantity": 3
                }
                """;

        // when
        mockMvc.perform(post("/v1/stock/{id}/buy", 111)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        // then
        verifyNoMoreInteractions(stockBuyCommand);
    }

    @Test
    void shouldNotAllowToBuyWhenQuantityIs0OrLess() throws Exception {
        // given
        String request = """
                {
                    "userId": 123,
                    "maxPrice": 12.12,
                    "quantity": 0
                }
                """;

        // when
        mockMvc.perform(post("/v1/stock/{id}/buy", 111)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

        // then
        verifyNoMoreInteractions(stockBuyCommand);
    }
}
