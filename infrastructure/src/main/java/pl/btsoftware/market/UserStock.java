package pl.btsoftware.market;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.btsoftware.market.common.StockBuyRequest;
import pl.btsoftware.market.common.StockId;
import pl.btsoftware.users.UserId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user_stock")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
class UserStock {
    @EmbeddedId
    private UserStockId id;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private boolean forSold;

    UserId getUserId() {
        return new UserId(id.getUserId());
    }

    StockId getStockId() {
        return new StockId(id.getStockId());
    }

    static UserStock from(UserId user, StockBuyRequest request) {
        return new UserStock(new UserStockId(user, request.stockId()), request.quantity().value(), request.maxPrice().value(), false);
    }
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserStockId implements Serializable {
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private int stockId;

    public UserStockId(UserId userId, StockId stockId) {
        this.userId = userId.value();
        this.stockId = stockId.value();
    }
}
