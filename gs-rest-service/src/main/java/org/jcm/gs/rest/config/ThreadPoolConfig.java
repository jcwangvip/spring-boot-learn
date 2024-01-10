package org.jcm.gs.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${tp.default.corePoolSize:1}")
    private int corePoolSize;

    @Value("${tp.default.maxPoolSize:1}")
    private int poolSize;

    @Value("${tp.default.keepAliveSeconds:60}")
    private int keepAliveSeconds;

    @Value("${tp.default.queueCapacity:20}")
    private int queueCapacity;


    /**
     * 创建线程池bean
     *
     * @return 线程池bean
     */
    @Bean(name = "defaultThreadPool")
    public ThreadPoolTaskExecutor defaultThreadPool() {
        return getThreadPool(corePoolSize, poolSize, keepAliveSeconds, queueCapacity);
    }


    private ThreadPoolTaskExecutor getThreadPool(int corePoolSize, int maxPoolSize,
                                                 int keepAliveSeconds, int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置线程空闲时间（秒），当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //CallerRunsPolicy 主线程中执行任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }


}
