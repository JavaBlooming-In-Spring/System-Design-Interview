package system.design.interview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Blob;

@Entity
public class Member {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String name;

  private Integer age;
  private Boolean sex;

  @Column(name = "profile_image")
  private Blob profileImage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;
}
