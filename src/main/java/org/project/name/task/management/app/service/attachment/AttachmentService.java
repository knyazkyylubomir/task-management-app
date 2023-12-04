package org.project.name.task.management.app.service.attachment;

import com.dropbox.core.DbxException;
import java.io.IOException;
import org.project.name.task.management.app.dto.attachment.AttachmentResponseDto;
import org.project.name.task.management.app.dto.attachment.AttachmentSearchParameter;
import org.project.name.task.management.app.dto.attachment.FileDownloadDto;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentResponseDto uploadAttachment(Long taskId, MultipartFile file)
            throws IOException, DbxException;

    FileDownloadDto searchAttachments(AttachmentSearchParameter searchParameter);
}
