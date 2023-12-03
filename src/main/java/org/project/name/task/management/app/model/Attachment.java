package org.project.name.task.management.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@SQLDelete(sql = "UPDATE attachments SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @Column(name = "dropbox_file_id", nullable = false)
    private String dropboxFileId;
    @Column(name = "filename", nullable = false)
    private String filename;
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime updateDate;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
