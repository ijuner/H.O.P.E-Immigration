package ca.conestoga.project.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * service that do job timely
 */
@Component
public class JobService {
    /**
     * start 0:10 everyday
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void doThings() {
        //
    }
}
