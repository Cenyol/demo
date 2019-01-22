package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Console;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) {
//		Flux.just("1", "2", "3", "4").subscribe(System.out::println);
//
//		Flux.range(0, 10).subscribe(System.out::println);
//		Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
//		Flux.range(5, 5).subscribe(i -> System.out.println(i));

		Mono.from(Mono.just("hello"))
				.doOnNext(System.out::print)
				.doOnNext(System.out::println)
				.block(Duration.ofSeconds(5));
	}

}
