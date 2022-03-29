package MadTests.TestForum.rep;

import MadTests.TestForum.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}