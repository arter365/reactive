package com.factorysalad.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class B_FluxTest {

    @Test
    public void fluxSubscriber() {
        Flux<String> fluxString = Flux.just("William", "Suane", "DevDojo", "Academy")
                .log();

        StepVerifier.create(fluxString)
                .expectNext("William", "Suane", "DevDojo", "Academy")
                .verifyComplete();
    }

    @Test
    public void fluxSubscriberNumbers() {
        // 숫자범위 평가
        Flux<Integer> fluxString = Flux.range(1, 5)
                .log();

        StepVerifier.create(fluxString)
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }
}
