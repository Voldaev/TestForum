package MadTests.TestForum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class RegistrationTest {
    

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12.10-bullseye")
            .withDatabaseName("forum")
            .withPassword("postgres")
            .withUsername("postgres");


    @DynamicPropertySource
    static void initProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("srping.datasource.password", postgreSQLContainer::getPassword);
    }


    @Test
    public void testErrorRegistration() {
        
    }

}
