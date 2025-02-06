package home.tm.repositories;

import home.tm.model.Exercise;
import home.tm.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByTraining(Training entity);
}
