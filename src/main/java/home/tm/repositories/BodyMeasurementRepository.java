package home.tm.repositories;

import home.tm.model.BodyMeasurement;
import home.tm.model.TrainingDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long>, JpaSpecificationExecutor<BodyMeasurement> {
    List<BodyMeasurement> findAllByTrainingDiary(TrainingDiary entity);
}
