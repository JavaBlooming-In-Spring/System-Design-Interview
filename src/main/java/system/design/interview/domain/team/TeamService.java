package system.design.interview.domain.team;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.design.interview.domain.team.dto.request.TeamCreateRequest;
import system.design.interview.domain.team.dto.request.TeamUpdateRequest;
import system.design.interview.domain.team.dto.response.TeamResponse;
import system.design.interview.domain.team.exception.TeamException;
import system.design.interview.domain.team.exception.TeamException.NotFoundTeam;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamCacheRepository teamCacheRepository;

    @Transactional
    public Long createTeam(TeamCreateRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .build();
        Team savedTeam = teamRepository.save(team);
        teamCacheRepository.save(savedTeam);
        return savedTeam.getId();
    }

    public List<TeamResponse> findAll() {
        return teamRepository.findAll()
                .stream()
                .map(TeamResponse::from)
                .toList();
    }

    @Transactional
    public void updateTeam(Long teamId, TeamUpdateRequest request) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(NotFoundTeam::new);
        team.update(request.getName());
        teamCacheRepository.save(team);
    }

    @Transactional
    public void deleteMemberById(Long teamId) {
        teamRepository.deleteById(teamId);
        teamCacheRepository.deleteById(teamId);
    }

    public Team findById(Long teamId) {
        Optional<Team> cacheTeam = teamCacheRepository.findById(teamId);
        if (cacheTeam.isPresent()) {
            return cacheTeam.get();
        }
        Team team = teamRepository.findById(teamId).orElseThrow(NotFoundTeam::new);
        teamCacheRepository.save(team);
        return team;
    }
}
