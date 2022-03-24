package MadTests.TestForum.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

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
    private Integer status; // fixme как поведёт себя enum с базой?

    @Column(name = "uuid")
    private String uuid;
}
