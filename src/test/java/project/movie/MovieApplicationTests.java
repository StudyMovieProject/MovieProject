package project.movie;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost:8080", description = "Default Server URL")
		}
)
class MovieApplicationTests {

	@Test
	void contextLoads() {
	}

}
