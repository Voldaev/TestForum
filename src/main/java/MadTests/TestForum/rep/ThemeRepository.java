package MadTests.TestForum.rep;

import MadTests.TestForum.model.SectionEntity;
import MadTests.TestForum.model.ThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
}