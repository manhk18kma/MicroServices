package TTCS.PostService.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary((Map) customCloudinaryConfig());
    }

    @Bean
    public Object customCloudinaryConfig() {
        return ObjectUtils.asMap(
                "cloud_name", "dhrcu7xli",
                "api_key", "766443287226568",
                "api_secret", "ZFiTMSxcLJRqXxET11Hb9CXriD8",
                "secure", true
        );
    }
}
