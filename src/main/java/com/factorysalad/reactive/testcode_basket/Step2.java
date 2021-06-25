package com.factorysalad.reactive.testcode_basket;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class Step2 {
    public static void main(String[] args) {
        Step2 step2 = new Step2();
        step2.basket();
    }

    // map은 javascript와 java의 스트림에서 사용하는 map과 동일한 기능을 수행한다.
    // flatMap
    // concatMap

    public void basket() {
        // 재료 ===========================================================================================

        // basket1부터 basket3까지 3개의 과일바구니가 있으며, 과일바구니 안에는 과일을 중복해서 넣을 수 있다.
        final List<String> basket1 = Arrays.asList("kiwi", "orange", "lemon", "orange", "lemon", "kiwi");
        final List<String> basket2 = Arrays.asList("banana", "lemon", "lemon", "kiwi");
        final List<String> basket3 = Arrays.asList("strawberry", "orange", "lemon", "grape", "strawberry");
        // 이 바구니를 List로 가지는 baskets
        final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
        // List를 벗기면 List<String>이 나온다. 이를 Flux로 만들어 준다. (Flux, Mono의 처리방식을 Map의 처리와 비슷하게 보면 된다.)
        final Flux<List<String>> basketFlux = Flux.fromIterable(baskets);

        Flux<Integer> seq = Flux.just(1, 2, 3)
                .flatMap(i ->
                    Flux.range(1, i)
                ); // Integeran를 Flux<Integer>로 1-N 변환

        //seq.subscribe(System.out::println);

        // ===============================================================================================

//        String text = "abcd";
//        Mono<String> mono = Mono.just(text);
//        mono.map(t -> t.length()).subscribe(p -> System.out.println(p));
//
//        Flux.just("asdf","sdfg")
//                .subscribe(System.out::println);


//        basketFlux.flatMap(basket -> {
//
//            System.out.println(basket);
//        }).subscribe(System.out::println);
    }
}
