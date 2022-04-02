package MadTests.TestForum;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CookieControl {

    public static HttpHeaders headers = new HttpHeaders();

    public static <T>HttpEntity<T> getBody(T body) {
        return new HttpEntity<T>(body, headers);
    }

    public static void updateCookie(ResponseEntity response) {
        List<String> list = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (list != null && list.size() > 0) {
            headers.addAll(HttpHeaders.COOKIE, list);
        }
    }

}
