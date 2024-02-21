package org.jcm.gs.rest.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 应用程序结束时,bean 销毁处理方式
 */
@Slf4j
@Component
@AllArgsConstructor
public final class CleanupBean implements DisposableBean {

    private final ThreadPoolTaskExecutor defaultThreadPool;


    @Override
    public void destroy() {
        log.info("starting clean");
        defaultThreadPool.destroy();
        log.info("finishing clean");
    }
}
