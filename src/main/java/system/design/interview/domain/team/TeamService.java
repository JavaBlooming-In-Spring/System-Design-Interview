package system.design.interview.domain.team;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.design.interview.domain.team.dto.request.TeamCreateRequest;
import system.design.interview.domain.team.dto.request.TeamUpdateRequest;
import system.design.interview.domain.team.dto.response.TeamResponse;
import system.design.interview.domain.team.exception.TeamException.NotFoundTeam;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamRedisRepository teamRedisRepository;

    @Transactional
    public Long createTeam(TeamCreateRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .build();
        Team savedTeam = teamRepository.save(team);
        teamRedisRepository.save(savedTeam.getId(), savedTeam);
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
        teamRedisRepository.save(teamId, team);
    }

    @Transactional
    public void deleteMemberById(Long teamId) {
        teamRepository.deleteById(teamId);
        teamRedisRepository.deleteById(teamId);
    }

    public Team findById(Long teamId) {
        Optional<Team> cacheTeam = teamRedisRepository.findById(teamId);
        System.out.println("cacheTeam = " + cacheTeam);
        if (cacheTeam.isPresent()) {
            return cacheTeam.get();
        }
        Team team = teamRepository.findById(teamId).orElseThrow(NotFoundTeam::new);
        teamRedisRepository.save(teamId, team);
        return team;
    }
}
