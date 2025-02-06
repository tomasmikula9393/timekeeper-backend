package home.tm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

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

    @Column(name = "weight", precision = 10, scale = 2)
    private Double weight;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "speed")
    private Double speed;

    @ManyToOne
    @JoinColumn(name = "training", referencedColumnName = "id_training", nullable = false)
    private Training training;
}
