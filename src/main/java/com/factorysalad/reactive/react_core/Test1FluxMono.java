package com.factorysalad.reactive.react_core;

import com.factorysalad.reactive.ReactiveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/*
https://godekdls.github.io/Reactor%20Core/reactorcorefeatures/ 사이트 학습
 */
public class Test1FluxMono {
    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test1FluxMono t1 = new Test1FluxMono();
        t1.test1();
        t1.test2();
    }

    public void test1() {
        System.out.println("test1 start -----");
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);
        System.out.println("test1 end -----");
    }

    public void test2() {
        System.out.println("test2 start -----");
        Mono<String> noData = Mono.empty();

        Mono<String> data = Mono.just("foo");

        Flux<Integer> numbersFromFiveToSeven = Flux.range(5,3);
        System.out.println("test2 end -----");
    }


}
