package home.tm.model;

import home.tm.model.enums.TrainingType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "training", schema = "time_keeper", indexes = {
        @Index(columnList = "id_training", name = "pk_id_training"),
})
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_training_seq")
    @SequenceGenerator(sequenceName = "id_training_seq", name = "id_training_seq", allocationSize = 1)
    @Column(name = "id_training")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private TrainingType type;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "day", length = 10,  nullable = false)
    private Integer day;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "name", length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_diary_id", referencedColumnName = "id_training_diary", nullable = false)
    private TrainingDiary trainingDiary;

    public void setExercises(List<Exercise> exercises) {
        this.exercises.clear();
        for (Exercise exercise : exercises) {
            exercise.setTraining(this);
        }
        this.exercises.addAll(exercises);
    }

}
