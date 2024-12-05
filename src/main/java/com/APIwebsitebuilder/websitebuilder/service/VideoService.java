s keypackage com.APIwebsitebuilder.websitebuilder.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoService {

    private final AmazonS3 amazonS3;
    private final String BUCKET_NAME = "video-storage-ram";

    public VideoService() {
        // Setup AWS credentials and S3 client
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials("key", "key2");
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("eu-north-1")  // Change to your region
                .build();
    }

    public String uploadFile(MultipartFile file) throws Exception {
        // Ensure the file is not empty
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }

        // Save file locally first (optional) before uploading to S3
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(tempFile);

        // Upload file to S3 bucket
        amazonS3.putObject(new PutObjectRequest(BUCKET_NAME, file.getOriginalFilename(), tempFile));

        // Generate S3 URL for the uploaded file
        return amazonS3.getUrl(BUCKET_NAME, file.getOriginalFilename()).toString();
    }
}
