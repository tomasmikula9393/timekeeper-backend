package home.tm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "training_diary", schema = "time_keeper", indexes = {
        @Index(columnList = "id_training_diary", name = "pk_id_training_diary"),
})
public class TrainingDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_training_diary_seq")
    @SequenceGenerator(sequenceName = "id_training_diary_seq", name = "id_training_diary_seq", allocationSize = 1)
    @Column(name = "id_training_diary")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "year", nullable = false)
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user", nullable = false)
    private User user;
}

