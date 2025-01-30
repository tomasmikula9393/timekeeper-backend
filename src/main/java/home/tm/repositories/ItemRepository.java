package home.tm.repositories;

import home.tm.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByValidityToIsBeforeAndStavIsNot(LocalDateTime now, String stav);
    List<Item> findAllByValidityToIsBetweenAndStavIsNot(LocalDateTime from, LocalDateTime to, String stav1);
    List<Item> findAllByValidityToIsBetweenAndStavIsNotAndStavIsNot(LocalDateTime from, LocalDateTime to, String stav1, String stav2);
}
