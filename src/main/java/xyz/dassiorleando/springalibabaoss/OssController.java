package xyz.dassiorleando.springalibabaoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * OSS Controller
 * @author dassiorleando
 */
@RestController
public class OssController {

    @Autowired
    private OSS ossClient;

    @GetMapping("/upload")
    public String upload() {
        try {
            ossClient.putObject("oss-test", "spring-alibaba-oss.json", this
                .getClass().getClassLoader().getResourceAsStream("spring-alibaba-oss.json"));
        } catch (Exception e) {
            e.printStackTrace();
            return "upload fail: " + e.getMessage();
        }
        return "upload success";
    }

    @GetMapping("/download")
    public String download() {
        try {
            OSSObject ossObject = ossClient.getObject(SpringAlibabaOssApplication.BUCKET_NAME, "spring-alibaba-oss.json");
            return "download success, content: " + IOUtils
                .readStreamAsString(ossObject.getObjectContent(), CharEncoding.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "download fail: " + e.getMessage();
        }
    }

    @GetMapping("/buckets/exist/{bucketName}")
    public boolean bucketExists(@PathVariable String bucketName) {
        return ossClient.doesBucketExist(bucketName);
    }

    @GetMapping("/objects/exist/{objectName}")
    public boolean objectExists(@PathVariable String objectName) {
        return ossClient.doesObjectExist(SpringAlibabaOssApplication.BUCKET_NAME, objectName);
    }

}
