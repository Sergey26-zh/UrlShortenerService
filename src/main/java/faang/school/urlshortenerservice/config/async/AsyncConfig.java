package faang.school.urlshortenerservice.config.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("customThreadPool")
    public ThreadPoolTaskExecutor customThreadPoolTaskExecutor(
            @Value("${async.pool.core-size}") int corePoolSize,
            @Value("${async.pool.max-size}") int maxPoolSize,
            @Value("${async.pool.queue-capacity}") int queueCapacity) {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("CustomThreadPool-");
        executor.initialize();
        return executor;
    }
}