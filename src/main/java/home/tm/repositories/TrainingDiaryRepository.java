package home.tm.repositories;

import home.tm.model.TrainingDiary;
import home.tm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDiaryRepository extends JpaRepository<TrainingDiary, Long> {
    List<TrainingDiary> findAllByUser(User currentUser);
}
