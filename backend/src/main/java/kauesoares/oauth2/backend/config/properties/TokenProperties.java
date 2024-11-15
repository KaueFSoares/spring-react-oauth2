package kauesoares.oauth2.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "security.token")
@Data
public class TokenProperties {

    private String secret;
    private Long expiration;
    private String issuer;

}
