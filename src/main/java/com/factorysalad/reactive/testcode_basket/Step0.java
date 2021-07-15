package com.factorysalad.reactive.testcode_basket;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class Step0 {
    public static void main(String[] args) {
        Step0 step0 = new Step0();
        step0.monoTest();
        step0.reduceTest();
    }

    public void monoTest() {
        String name = "andrew";
        /*
        // 아래와 같이 작성하면 에러가 발생한다.
        Mono<String> mono = Mono.just(name)
                .log()
                .subscribe();
        */
        Mono.just(name)
                .log()
                .subscribe();
        /*
        11:05:56.039 [main] INFO reactor.Mono.Just.1 - | onSubscribe([Synchronous Fuseable] Operators.ScalarSubscription)
        11:05:56.044 [main] INFO reactor.Mono.Just.1 - | request(unbounded)
        11:05:56.044 [main] INFO reactor.Mono.Just.1 - | onNext(andrew)
        11:05:56.049 [main] INFO reactor.Mono.Just.1 - | onComplete()
         */
    }

    public void manualTest() {
        Flux<String> seq = Flux.just("빨강", "초록", "파랑")
                .flatMap(ball ->
                        // Map과 달리 하나가 들어갔을 때 한개 이상이 나올 수 있다는 것이 중요함.
                        Flux.just(ball + " 다이아", ball + " 다이아")
                ); // Integeran를 Flux<Integer>로 1-N 변환
        seq.subscribe(System.out::println);
    }

    public void reduceTest() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        Integer sum1 = numbers.stream().reduce(0,Integer::sum);
        System.out.println("sum1 : " + sum1);

        int sum2 = numbers.stream().mapToInt(i -> i).sum();

    }

}
