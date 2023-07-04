package system.design.interview.domain.team;

import jakarta.persistence.*;
import lombok.*;
import system.design.interview.domain.BaseEntity;

@Getter
@Entity
@Table(name = "team")
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Builder
    private Team(final String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
