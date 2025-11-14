package home.tm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "exercise", schema = "time_keeper", indexes = {
        @Index(columnList = "id_exercise", name = "pk_id_exercise"),
})
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_exercise_seq")
    @SequenceGenerator(sequenceName = "id_exercise_seq", name = "id_exercise_seq", allocationSize = 1)
    @Column(name = "id_exercise")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "set")
    private Integer set;

    @Column(name = "rep")
    private Integer rep;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "speed")
    private Double speed;

    @ManyToOne
    @JoinColumn(name = "training_id", referencedColumnName = "id_training", nullable = false)
    private Training training;
}
