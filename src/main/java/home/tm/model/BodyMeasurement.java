package home.tm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "body_measurement", schema = "time_keeper", indexes = {
        @Index(columnList = "id_body_measurement", name = "pk_id_body_measurement"),
})
public class BodyMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_body_measurement_seq")
    @SequenceGenerator(sequenceName = "id_body_measurement_seq", name = "id_body_measurement_seq", allocationSize = 1)
    @Column(name = "id_body_measurement")
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "body_fat")
    private Double bodyFat;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "day", length = 10,  nullable = false)
    private Integer day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_diary_id", referencedColumnName = "id_training_diary", nullable = false)
    private TrainingDiary trainingDiary;
}
