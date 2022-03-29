package MadTests.TestForum.rep;

import MadTests.TestForum.model.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<SectionEntity, String> {
}