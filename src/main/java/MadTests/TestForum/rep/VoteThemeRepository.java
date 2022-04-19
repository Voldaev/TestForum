package MadTests.TestForum.rep;

import MadTests.TestForum.model.VoteThemeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteThemeRepository extends JpaRepository<VoteThemeEntity, Long> {
    @Query("select distinct v from VoteThemeEntity v where (v.theme.id = :themeId) and (v.user.id = :sessionUserId)")
    VoteThemeEntity check(Long themeId, Long sessionUserId);
}