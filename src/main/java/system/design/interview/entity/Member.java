package system.design.interview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer age;
  private Boolean sex;

  @Column(name = "profile_image")
  private Byte[] profileImage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;
}
