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
