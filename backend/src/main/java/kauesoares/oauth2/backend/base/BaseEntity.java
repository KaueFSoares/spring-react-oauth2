package kauesoares.oauth2.backend.base;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    protected Boolean deleted;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

}
