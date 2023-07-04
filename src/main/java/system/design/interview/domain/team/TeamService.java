package system.design.interview.domain.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.design.interview.domain.team.dto.request.TeamCreateRequest;
import system.design.interview.domain.team.dto.request.TeamUpdateRequest;
import system.design.interview.domain.team.dto.response.TeamResponse;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamService {

    private final TeamCacheableRepository teamCacheableRepository;

    @Transactional
    public Long createTeam(TeamCreateRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .build();
        Team savedTeam = teamCacheableRepository.save(team);
        return savedTeam.getId();
    }

    public List<TeamResponse> findAll() {
        return teamCacheableRepository.findAll()
                .stream()
                .map(TeamResponse::from)
                .toList();
    }

    @Transactional
    public void updateTeam(Long teamId, TeamUpdateRequest request) {
        teamCacheableRepository.update(teamId, request.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
    }

    @Transactional
    public void deleteMemberById(Long teamId) {
        teamCacheableRepository.deleteById(teamId);
    }

    public Team findById(Long teamId) {
        return teamCacheableRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
    }
}
