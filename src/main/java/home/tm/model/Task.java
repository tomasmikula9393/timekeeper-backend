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
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "task", schema = "time_keeper", indexes = {
        @Index(columnList = "id_task", name = "pk_id_task"),
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_task_seq")
    @SequenceGenerator(sequenceName = "id_task_seq", name = "id_task_seq", allocationSize = 1)
    @Column(name = "id_task")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "deadline")
    private LocalDateTime deadline;
}
