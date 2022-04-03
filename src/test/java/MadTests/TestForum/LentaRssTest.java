package MadTests.TestForum;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Тесты связанные с получением новостей с сайта lenta.ru
 */
public class LentaRssTest {
    
    /**
     * Тест по получению новостей с сайта lenta.ru
     * @throws Exception
     */
    @Test
    public void testRss() throws Exception {
    
        ObjectMapper objectMapper = XmlMapper.builder().build();

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject("https://lenta.ru/rss", String.class);

        //System.out.println(response);

        Map map = objectMapper.readValue(response, Map.class);

        map = (Map)map.get("channel");

        Collection collection = (Collection)map.get("item");

        for (Object item : collection) {
            map = (Map)item;
            String title = (String)map.get("title");
            String description = (String)map.get("description");
        }

        System.out.println(objectMapper.readValue(response, Map.class));

    }


}
