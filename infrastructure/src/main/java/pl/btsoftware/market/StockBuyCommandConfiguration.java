package pl.btsoftware.market;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.btsoftware.users.VerifyUser;

@Configuration
@AllArgsConstructor
class StockBuyCommandConfiguration {
    private final VerifyUser verifyUser;
    private final TransactionJpaRepository transactionJpaRepository;
    @Bean
    StockBuyCommand stockBuyCommand() {
        return new StockBuyCommandResolver(verifyUser, transactionJpaRepository);
    }
}
