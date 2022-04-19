package MadTests.TestForum.rep;

import MadTests.TestForum.model.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
    ThemeEntity getByTheme(String theme);
}