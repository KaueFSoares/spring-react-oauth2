package kauesoares.oauth2.backend.config.docs;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    private static final String REDIRECT_URL = "{frontEndUrl}/success?token=TOKEN&refresh=REFRESH&error=ERROR";

    @Bean
    public OpenApiCustomizer customOpenApi() {
        return openApi -> {
            openApi.addTagsItem(new Tag().name("oauth2"));

            Paths paths = openApi.getPaths();

            PathItem googleAuthPathItem = new PathItem()
                    .get(new Operation()
                            .summary("Google login")
                            .description("Endpoint for Google OAuth2 authentication. After successful authentication, the user will be redirected to:\n\n" +
                                    "`" + REDIRECT_URL + "`\n\n" +
                                    "Params:\n" +
                                    "- `token`: JWT access token\n\n" +
                                    "- `refresh`: JWT refresh token\n\n" +
                                    "- `error`: Error message")
                            .addTagsItem("oauth2")
                            .responses(createCommonResponses()));

            paths.addPathItem("/oauth2/authorization/google", googleAuthPathItem);

            PathItem githubAuthPathItem = new PathItem()
                    .get(new Operation()
                            .summary("GitHub login")
                            .description("Endpoint for GitHub OAuth2 authentication. After successful authentication, the user will be redirected to:\n\n" +
                                    "`" + REDIRECT_URL + "`\n\n" +
                                    "Params:\n" +
                                    "- `token`: JWT access token\n\n" +
                                    "- `refresh`: JWT refresh token\n\n" +
                                    "- `error`: Error message")
                            .addTagsItem("oauth2")
                            .responses(createCommonResponses()));

            paths.addPathItem("/oauth2/authorization/github", githubAuthPathItem);
        };
    }

    private ApiResponses createCommonResponses() {
        ApiResponses responses = new ApiResponses();
        responses.addApiResponse("302", new ApiResponse()
                .description("Redirects to the OAuth2 provider login page")
                .content(new Content().addMediaType("text/html", new MediaType())));
        return responses;
    }

}
