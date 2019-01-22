package com.example.demo.controller;

import com.example.demo.meta.User;
import com.example.demo.service.AOPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/11/13 17:28
 */

@Slf4j
@RestController
public class TestController {

    @Autowired
    private AOPService aopService;

    private ThreadLocal<Long> opIdThreadLocal = new ThreadLocal<>();

    @RequestMapping(value = "/test")
    public String test(@RequestBody Map<Long, User> userMap) {
        userMap.forEach((k, v) -> {
            log.info("k:{}, v:{}", k, v);
        });
        return "Hello";
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "chen"));
        users.add(new User(2, "han"));
        users.add(new User(3, "qun"));

        users.forEach(System.out::println);

        List<User> users1 = users;//new ArrayList<>(users);
        users1.add(new User(4, "mei"));

        users.forEach(System.out::println);
        users1.forEach(System.out::println);
    }

    @GetMapping(value = "/test01")
    public String test01(String s, HttpServletRequest requests) {
        opIdThreadLocal.set(0L);
        opIdThreadLocal.remove();
        log.info("opIdThreadLocal {}.", opIdThreadLocal.get() == null);

        aopService.groupUpdate(2.0);

        log.info("mvc-start");
        String result = doSomething("MVC");
        log.info("mvc-end");
        return result;
    }

    @GetMapping(value = "/test02")
    public Mono<String> test02() {
        aopService.groupUpdate1(new User(), 2);
        aopService.groupUpdate12(new ArrayList<>(Arrays.asList(1,2,3,4)));
        aopService.groupUpdate123(1);

        log.info("mono-start");
        Mono<String> result = Mono.fromSupplier(() -> doSomething("Mono"));
        log.info("mono-end");
        return result;
    }

    private String doSomething(String s) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + s;
    }

}









