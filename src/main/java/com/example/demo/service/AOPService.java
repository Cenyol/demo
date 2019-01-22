package com.example.demo.service;

import com.example.demo.meta.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/12/27 09:58
 */
@Slf4j
@Service
public class AOPService {
    public User groupUpdate(Double d) {
        log.info("groupUpdate i");
        return null;
    }
    public Double groupUpdate123(Integer i) {
        log.info("groupUpdate d");
        return 9.8;
    }
    public Double groupUpdate12(List<Integer> list) {
        log.info("groupUpdate d");
        return 9.8;
    }
    public Double groupUpdate1(User i, Integer j) {
        log.info("groupUpdate d");
        return 9.8;
    }
}
