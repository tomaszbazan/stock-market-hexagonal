package pl.btsoftware.market;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import pl.btsoftware.market.common.StockBuyRequest;
import pl.btsoftware.users.UserId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

import static pl.btsoftware.JsonConfiguration.json;

@Data
@Entity
@Table(name = "transaction_events")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
class UserTransactionEvent {
    @Id
    UUID eventId;
    @Column(nullable = false)
    int userId;
    @Column(nullable = false)
    Instant occurrenceTime;
    @Column(columnDefinition = "jsonb", nullable = false)
    @Type(type = "jsonb")
    private String payload;

    static UserTransactionEvent buyRequest(UserId user, StockBuyRequest buyRequest) {
        return new UserTransactionEvent(UUID.randomUUID(), user.value(), Instant.now(), json(StockBoughtV1.from(buyRequest)));
    }

    static UserTransactionEvent soldRequest(UserId user, UserStock soldRequest) {
        return new UserTransactionEvent(UUID.randomUUID(), user.value(), Instant.now(), json(StockSoldV1.from(soldRequest)));
    }
}
