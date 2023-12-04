package org.project.name.task.management.app.repository.attachment;

import java.util.Optional;
import org.project.name.task.management.app.model.Attachment;
import org.project.name.task.management.app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository
        extends JpaRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {
    Optional<Attachment> findByTaskAndDropboxFileId(Task task, String dropboxFileId);
}
