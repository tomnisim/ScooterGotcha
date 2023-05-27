package gotcha.server.Domain.s3;

import gotcha.server.Utils.Logger.ErrorLogger;
import gotcha.server.Utils.Logger.ServerLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final ServerLogger serverLogger;
    private final ErrorLogger errorLogger;
    private final S3Bucket s3Bucket;

    @Autowired
    public S3Service(S3Client s3Client, ServerLogger serverLogger, ErrorLogger errorLogger, S3Bucket bucket) {
        this.s3Client = s3Client;
        this.serverLogger = serverLogger;
        this.errorLogger = errorLogger;
        this.s3Bucket = bucket;
    }

    public void putImage(String key, byte[] image) {
        var putRequest = PutObjectRequest.builder()
                .bucket(s3Bucket.getBucketName())
                .key(key)
                .build();
        s3Client.putObject(putRequest, RequestBody.fromBytes(image));
        serverLogger.add_log("Stored image in s3 bucket: " + s3Bucket.getBucketName() + " with key: " + key);
    }

    public byte[] getImage(String key) {
        var getRequest = GetObjectRequest.builder()
                .bucket(s3Bucket.getBucketName())
                .key(key)
                .build();
        var response = s3Client.getObject(getRequest);
        try {
            var image = response.readAllBytes();
            serverLogger.add_log("Retrieved image from s3 bucket: " + s3Bucket.getBucketName() + " with key: " + key);
            return image;
        } catch (IOException e) {
            errorLogger.add_log("Failed to retrieve image from s3 bucket: " + s3Bucket.getBucketName() + " with key: " + key);
            throw new RuntimeException(e);
        }
    }

    public void saveAllImages() {
        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(s3Bucket.getBucketName())
                .build();
        String initialName = "image";
        int i = 1;
        ListObjectsV2Response listObjResponse = s3Client.listObjectsV2(listObjectsReqManual);
        for (S3Object content : listObjResponse.contents()) {
            String objectKey = content.key();

            ResponseBytes<GetObjectResponse> getObjectResponseBytes =
                    s3Client.getObject(GetObjectRequest.builder()
                                    .bucket(s3Bucket.getBucketName())
                                    .key(objectKey)
                                    .build(),
                            ResponseTransformer.toBytes());

            byte[] data = getObjectResponseBytes.asByteArray();

            // Write to a file in the filesystem
            try {
                var targetDirectory = "src/main/java/gotcha/server/Service/" + initialName + i + ".jpg";
                i++;
                Files.write(Path.of(targetDirectory), data, StandardOpenOption.CREATE);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public void deleteImage(String key) {
        try {
            var deleteRequest = DeleteObjectRequest.builder()
                    .bucket(s3Bucket.getBucketName())
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
            serverLogger.add_log("Deleted image from s3 bucket: " + s3Bucket.getBucketName() + " with key: " + key);
        }
        catch (Exception e) {
            errorLogger.add_log("Failed to delete image from s3 bucket: " + s3Bucket.getBucketName() + " with key: " + key);
            throw new RuntimeException(e);
        }
    }
}
