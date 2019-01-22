package com.example.demo.meta.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2019/1/14 15:14
 */
@Slf4j
@Component
public class ListenerBean {
    @EventListener
    public void handleEvent(RequestHandledEvent requestHandledEvent) {
        log.info("sessionId:{}.", requestHandledEvent);
    }


    public void requestHandle() {

    }
}