package system.design.interview.domain.team;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.design.interview.domain.team.dto.request.TeamCreateRequest;
import system.design.interview.domain.team.dto.request.TeamUpdateRequest;
import system.design.interview.domain.team.dto.response.TeamResponse;

@RequiredArgsConstructor
@RequestMapping("/teams")
@RestController
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Void> createTeam(@RequestBody TeamCreateRequest request) {
        Long teamId = teamService.createTeam(request);
        return ResponseEntity.created(URI.create("/teams/" + teamId)).build();
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> findAll() {
        List<TeamResponse> response = teamService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> findTeamById(
            @PathVariable(name = "id") Long teamId
    ) {
        teamService.findById(teamId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTeam(
            @PathVariable(name = "id") Long teamId,
            @RequestBody TeamUpdateRequest request
    ) {
        teamService.updateTeam(teamId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable(name = "id") Long teamId) {
        teamService.deleteMemberById(teamId);
        return ResponseEntity.ok().build();
    }
}
