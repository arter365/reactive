package com.factorysalad.reactive;

import com.factorysalad.reactive.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReactiveApplication.class, args);
	}

	//---------------------------------------------------------------------------------------------------

	@Override
	public void run(String... args) throws Exception {
		reactor1();
	}

	public void reactor1() {
		Mono.just(new Person(1, "이종석", 46))		// 이 줄만 있으면 아무것도 일어나지 않는다. 파이프라인만 만들었을 뿐이다.
				.subscribe(p -> log.info("[Reactor] Person: " + p));
	}
}

