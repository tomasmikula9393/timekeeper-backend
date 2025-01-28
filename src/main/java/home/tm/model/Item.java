package home.tm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "item", schema = "time_keeper", indexes = {
        @Index(columnList = "id_item", name = "pk_id_item"),
})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_item_seq")
    @SequenceGenerator(sequenceName = "id_item_seq", name = "id_item_seq", allocationSize = 1)
    @Column(name = "id_item")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "validity_from")
    private LocalDateTime validityFrom;

    @Column(name = "validity_to", nullable = false)
    private LocalDateTime validityTo;

    @Column(name = "stav", nullable = false, length = 20)
    private String stav;
}
