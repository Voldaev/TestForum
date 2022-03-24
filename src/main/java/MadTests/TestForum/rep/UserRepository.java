package MadTests.TestForum.rep;

import MadTests.TestForum.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findBySign(String sign);

    @Query("select u from UserEntity u where lower(u.mail) = trim(lower(:mail))")
    UserEntity findByEmail(String mail);

    UserEntity findByUuid(String uuid);
}