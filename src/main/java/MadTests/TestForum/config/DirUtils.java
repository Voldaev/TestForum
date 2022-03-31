package MadTests.TestForum.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

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

    public String getFileExt(final String filename) {
        String ext = null;
        if (filename != null && filename.contains(".")) {
            int i = filename.lastIndexOf(".");
            ext = filename.substring(i + 1);
        }
        ext = StringUtils.trimToNull(ext);
        ext = StringUtils.lowerCase(ext);
        return ext;
    }

    public boolean isImage(final String filename) {
        String ext = getFileExt(filename);
        if (ext == null) {
            return false;
        }
        switch (ext) {
            case "jpg":
            case "jpeg":
            case "png":
                return true;
        }
        return false;
    }

}
