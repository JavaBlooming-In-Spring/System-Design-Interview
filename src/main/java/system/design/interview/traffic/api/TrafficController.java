package system.design.interview.traffic.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.design.interview.traffic.application.TrafficService;

@RequiredArgsConstructor
@RequestMapping("/traffic")
@RestController
public class TrafficController {

    private final TrafficService trafficService;

    @GetMapping("/sync")
    public String syncTraffic(@RequestParam("second") int durationOfSecond) {
        return trafficService.generateSyncTraffic(durationOfSecond);
    }

    @GetMapping("/non-sync")
    public String nonSyncTraffic(@RequestParam("second") int durationOfSecond) {
        trafficService.generateNonSyncTraffic(durationOfSecond);
        return "Finish";
    }
}
