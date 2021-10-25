package io.github.jartool.console.config;

import io.github.jartool.console.queue.ConcurrentEvictingQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ConsoleConfig
 *
 * @author jartool
 */
@Configuration
@ComponentScan("io.github.jartool.console.**")
public class ConsoleConfig {

    @Value("${jartool.console.queue.size:1000}")
    private int maxSize;

    @Bean
    public ConcurrentEvictingQueue queue() {
        return new ConcurrentEvictingQueue(maxSize);
    }
}
