package system.design.interview.domain.member.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import system.design.interview.domain.member.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private Long memberId;
    private Long teamId;
    private String name;
    private Integer age;
    private Boolean sex;
    private String profileImage;

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
                member.getId(),
                member.getTeam().getId(),
                member.getName(),
                member.getAge(),
                member.getSex(),
                member.getProfileImage()
        );
    }
}
