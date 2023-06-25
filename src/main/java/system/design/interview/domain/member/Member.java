package system.design.interview.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import system.design.interview.domain.team.Team;

@Getter
@Entity
@Table(name = "member")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "sex", nullable = false)
    private Boolean sex;

    @Lob
    @Column(name = "profile_image")
    private String profileImage;

    @Builder
    private Member(Team team, String name, Integer age, Boolean sex, String profileImage) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.profileImage = profileImage;
    }

    public void update(Team team, String name, Integer age, Boolean sex, String profileImage) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.profileImage = profileImage;
    }
}
