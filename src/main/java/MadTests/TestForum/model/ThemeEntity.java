package MadTests.TestForum.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "themes")
public class ThemeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_name", nullable = false)
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity themeCreator;

    @Column(name = "theme")
    private String theme;

    @Column(name = "text")
    private String text;

    @Column(name = "published")
    private LocalDateTime published;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theme")
    private List<CommentEntity> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "theme")
    private List<VoteThemeEntity> votes;

}
