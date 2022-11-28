package pl.btsoftware.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.btsoftware.market.common.StockId;
import pl.btsoftware.users.UserId;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
interface UserStockJpaRepository extends JpaRepository<UserStock, UserStockId> {
    Optional<UserStock> findFirstByForSoldIsTrueAndIdStockIdEqualsAndAndQuantityEqualsAndAndPriceIsLessThan(int stockId, int quantity, BigDecimal maxPrice);
}
