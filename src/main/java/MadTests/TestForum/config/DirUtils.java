package MadTests.TestForum.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class DirUtils {

    @Value("${users.file.directory}")
    String userDir;

    @PostConstruct
    void initUserDir() {
        try {
            userDir = StringUtils.trimToNull(userDir);
            File file = new File(userDir);
            if (file.exists()) {
                log.debug("userDir {} exists", file.getAbsolutePath());
            } else if (file.mkdirs()) {
                log.debug("userDir {} create", file.getAbsolutePath());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String getUserDir() {
        return userDir;
    }
}
