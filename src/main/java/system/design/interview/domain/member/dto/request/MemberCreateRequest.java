package system.design.interview.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequest {

    private Long teamId;
    private String name;
    private Integer age;
    private Boolean sex;
    private String profileImage;
}
