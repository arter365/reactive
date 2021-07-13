package com.factorysalad.reactive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class A_MonoTest {

    @Test
    public void monoSubscriber() {
        String name = "William Suane";
        /*
        // 아래와 같이 작성하면 에러가 발생한다.
        Mono<String> mono = Mono.just(name)
                .log()
                .subscribe();
        */
        Mono<String> mono = Mono.just(name)
                .log(); // Mono를 구독하면서의 동작을 log로 보여준다.
        mono.subscribe();
        log.info("-------------------------");
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }
}
