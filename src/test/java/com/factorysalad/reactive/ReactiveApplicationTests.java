package com.factorysalad.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/*
https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/StepVerifier 사이트 학습
reactor-test는 임의의 Publisher를 구독 할 수 있다.
여기서 Flux 또는 Mono를 파라메터로 받아서 StepVerifier를 만들어서 테스트를 할 것이다.
반드시 verify 메서드를 마지막에 호출해야 한다. 그렇지 않으면 subscribe 하지 않고 검증 할 수 없다.
 */
@SpringBootTest
class ReactiveApplicationTests {

	@Test
	void contextLoads() {
		Flux<String> flux = Flux.just("foo", "bar");

		StepVerifier.create(flux)
				.expectNext("foo")
				.expectNext("bar")
				.verifyComplete();
	}

}
