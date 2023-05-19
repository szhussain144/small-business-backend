package backend.smallbusiness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime updatedDate;
    @CreatedBy
    private Long createdBy;
    @LastModifiedBy
    private Long updatedBy;
    @JsonIgnore
    private boolean isDeletedRecord;

    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }
    public LocalDateTime getCreatedAt() {
        return createdDate;
    }

    public BaseEntity setCreatedAt(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public BaseEntity setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public BaseEntity setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public BaseEntity setModifiedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public boolean isDeletedRecord() {
        return isDeletedRecord;
    }

    public BaseEntity setDeletedRecord(boolean deletedRecord) {
        this.isDeletedRecord = deletedRecord;
        return this;
    }
}
