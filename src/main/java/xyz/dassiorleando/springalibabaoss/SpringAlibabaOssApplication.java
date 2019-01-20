package xyz.dassiorleando.springalibabaoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;

/**
 * SpringAlibabaOss Application
 * @author dassiorleando
 */
@SpringBootApplication
public class SpringAlibabaOssApplication {

    public static final String BUCKET_NAME = "oss-test-2";

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(SpringAlibabaOssApplication.class, args);
    }

    @Bean
    public AppRunner appRunner() {
        return new AppRunner();
    }

    class AppRunner implements ApplicationRunner {
        @Autowired
        private OSS ossClient;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            try {
                if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                    ossClient.createBucket(BUCKET_NAME);

                    // Define the ACL for a bucket to be private on both read and write
                    ossClient.setBucketAcl(BUCKET_NAME, CannedAccessControlList.Private);
                }
            } catch (Exception e) {
                System.err.println("oss handle bucket error: " + e.getMessage());
                System.exit(-1);
            }
        }
    }

}
