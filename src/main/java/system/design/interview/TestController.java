package system.design.interview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    private int cnt = 0;
    @GetMapping("/test")
    public String test() {
        cnt++;
//        System.out.println("" + cnt);
        return "test";
    }
}
