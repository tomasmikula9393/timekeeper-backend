package home.tm.repositories;

import home.tm.model.Training;
import home.tm.model.TrainingDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {

    List<Training> findAllByTrainingDiary(TrainingDiary trainingDiary);
}
