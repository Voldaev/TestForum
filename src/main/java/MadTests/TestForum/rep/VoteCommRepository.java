package MadTests.TestForum.rep;

import MadTests.TestForum.model.VoteCommEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteCommRepository extends JpaRepository<VoteCommEntity, Long> {
    @Query("select distinct v from VoteCommEntity v where (v.comm.id = :commId) and (v.user.id = :sessionUserId)")
    VoteCommEntity check(Long commId, Long sessionUserId);
}