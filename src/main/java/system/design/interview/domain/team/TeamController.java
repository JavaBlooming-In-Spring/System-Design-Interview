package system.design.interview.domain.team;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.design.interview.domain.team.dto.request.TeamCreateRequest;
import system.design.interview.domain.team.dto.request.TeamUpdateRequest;
import system.design.interview.domain.team.dto.response.TeamResponse;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<TeamResponse> findById(@PathVariable(name = "id") Long teamId) {
        TeamResponse response = TeamResponse.from(teamService.findById(teamId));
        return ResponseEntity.ok(response);
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
