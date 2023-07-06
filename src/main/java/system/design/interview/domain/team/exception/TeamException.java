package system.design.interview.domain.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TeamException extends RuntimeException{
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public static class NotFoundTeam extends TeamException {

    }
}
