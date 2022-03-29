package MadTests.TestForum.rep;

import MadTests.TestForum.model.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
}