package home.tm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "transactions", schema = "time_keeper", indexes = {
        @Index(columnList = "id_transaction", name = "pk_id_transaction"),
})
public class Transactions {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_transaction_seq")
    @SequenceGenerator(sequenceName = "id_transaction_seq", name = "id_transaction_seq", allocationSize = 1)
    @Column(name = "id_transaction")
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user", nullable = false)
    private User user;
}
