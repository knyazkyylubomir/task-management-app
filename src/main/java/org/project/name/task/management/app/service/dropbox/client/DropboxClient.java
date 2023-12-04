package org.project.name.task.management.app.service.dropbox.client;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import java.io.IOException;
import java.io.InputStream;
import org.project.name.task.management.app.dto.attachment.FileDownloadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DropboxClient {
    private static DbxRequestConfig config;
    @Value("${dropbox.access.token}")
    private String accessToken;

    public DropboxClient() {
        config = DbxRequestConfig.newBuilder("dropbox/task-management-app").build();
    }

    public FileMetadata uploadFile(MultipartFile file) {
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        try (InputStream inputStream = file.getInputStream()) {
            return client.files().uploadBuilder(
                            "/task-management-app/attachments/" + file.getOriginalFilename()
                    )
                    .uploadAndFinish(inputStream);
        } catch (IOException | DbxException e) {
            throw new RuntimeException("Cannot upload a file to dropbox", e);
        }
    }

    public FileDownloadDto downloadFiles(String dropboxFileId) {
        DbxClientV2 client = new DbxClientV2(config, accessToken);
        try (DbxDownloader<FileMetadata> fileMetadataDbxDownloader = client.files()
                .downloadBuilder(dropboxFileId).start();
                InputStream inputStream = fileMetadataDbxDownloader.getInputStream()) {
            byte[] file = inputStream.readAllBytes();
            String fileName = fileMetadataDbxDownloader.getResult().getName();
            String fileExtension = getFileExtension(fileName);
            return new FileDownloadDto(file, fileName, fileExtension);
        } catch (IOException | DbxException e) {
            throw new RuntimeException("Cannot download a file to dropbox", e);
        }
    }

    private String getFileExtension(String fileName) {
        StringBuilder toReverseContentType = new StringBuilder(fileName);
        toReverseContentType.reverse();
        int indexOfDot = toReverseContentType.indexOf(".");
        String reversedFileExtension = toReverseContentType.substring(0, indexOfDot);
        return new StringBuilder(reversedFileExtension).reverse().toString();
    }
}
