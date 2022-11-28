package pl.btsoftware.market;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface TransactionEventsJpaRepository extends JpaRepository<UserTransactionEvent, UUID> {
}
