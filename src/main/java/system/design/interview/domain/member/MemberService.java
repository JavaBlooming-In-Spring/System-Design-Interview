package system.design.interview.domain.member;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.design.interview.domain.member.dto.request.MemberCreateRequest;
import system.design.interview.domain.member.dto.request.MemberUpdateRequest;
import system.design.interview.domain.member.dto.response.MemberResponse;
import system.design.interview.domain.team.Team;
import system.design.interview.domain.team.TeamRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long createMember(MemberCreateRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        Member member = Member.builder()
                .team(team)
                .name(request.getName())
                .age(request.getAge())
                .sex(request.getSex())
                .profileImage(request.getProfileImage())
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        member.update(
                team,
                request.getName(),
                request.getAge(),
                request.getSex(),
                request.getProfileImage()
        );
    }

    @Transactional
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
