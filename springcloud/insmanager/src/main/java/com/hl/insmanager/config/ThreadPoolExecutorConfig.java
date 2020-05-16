package com.hl.insmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @author ivan.huang
 */
@Configuration
public class ThreadPoolExecutorConfig {

    @Bean("defaultExecutorService")
    public ExecutorService init() {
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(10, 20, 30L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }
}
