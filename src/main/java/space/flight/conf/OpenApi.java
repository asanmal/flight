package space.flight.conf;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi {

    @Bean
    public OpenAPI dronesOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Drones API")
                        .description("API para gestionar drones y su matriz")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación de OpenAPI")
                        .url("https://swagger.io/docs/"));
    }
}
