package MadTests.TestForum;

import MadTests.TestForum.dto.MessageDTO;
import MadTests.TestForum.dto.UserRegDTO;
import MadTests.TestForum.service.MailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }


    @LocalServerPort
    int port;

    @Value("${spring.mvc.servlet.path}")
    String basePath;

    public String getUrl(String url) {
        String res = "http://localhost:" + port;
        if (!basePath.startsWith("/")) {
            res = res + "/";
        }
        res = res + basePath;
        if (res.endsWith("/") && url.startsWith("/")) {
            url = url.substring(1);
        }
        if (!res.endsWith("/") && !url.startsWith("/")) {
            res = res + "/";
        }
        return res + url;
    }

    @Autowired
    TestRestTemplate testRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);

    @MockBean
    MailService mailService;

    @Test
    public void testRegistration() {
        Mockito.when(mailService.sendSimpleMessage(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                .thenReturn(true);

        UserRegDTO userRegDTO = new UserRegDTO();
        userRegDTO.setName("GoodTestName");
        userRegDTO.setMail("test@test.ru");
        userRegDTO.setPass("GoodTestPassword");
        userRegDTO.setSign("GoodTestSign");

        String url = getUrl("/api/registration/registration");

        ResponseEntity<MessageDTO> response = testRestTemplate.postForEntity(url,
                new HttpEntity<>(userRegDTO, new HttpHeaders()),
                MessageDTO.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        Assertions.assertNotNull(response.getBody());

        Assertions.assertTrue(response.getBody().isSuccess());

        Assertions.assertEquals(response.getBody().getMessage(),"Успех");
    }

}
