package home.tm.model.base;

import home.tm.model.listeners.ModelDataListener;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Component
@MappedSuperclass
@EntityListeners({ModelDataListener.class})
@Getter
@Setter
public abstract class ModelData {

    @Column(name = "CREATED", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "UPDATED")
    private LocalDateTime updated;

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}

