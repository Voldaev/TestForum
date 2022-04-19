package MadTests.TestForum.model;

import MadTests.TestForum.model.enums.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sign")
    private String sign;

    @Column(name = "pass")
    private String pass;

    @Column(name = "mail")
    private String mail;

    @Column(name = "status")
    private Status status; // fixme как поведёт себя enum с базой?

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "avatar")
    private String avatar;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "themeCreator")
    private List<ThemeEntity> createdThemes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commCreator")
    private List<CommentEntity> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<VoteThemeEntity> themeVotes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<VoteCommEntity> commVotes;
}
