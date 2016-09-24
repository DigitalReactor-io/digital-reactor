package io.digitalreactor.report;

import io.digitalreactor.report.service.ReportScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ingvard on 13.09.16.
 */
@Configuration
@EnableScheduling
public class ReportConfig implements SchedulingConfigurer {
    @Bean
    public ReportScheduler reportScheduler() {
        return new ReportScheduler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(1);
    }
}
