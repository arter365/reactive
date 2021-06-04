package com.factorysalad.reactive.react_core;

import com.factorysalad.reactive.ReactiveApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/*
https://godekdls.github.io/Reactor%20Core/reactorcorefeatures/ 사이트 학습
 */
public class Test2Subscribe {
    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test2Subscribe t2 = new Test2Subscribe();
        t2.test1();
        t2.test2();
        t2.test3();
        t2.test4();
    }

    public void test1() {
        System.out.println("test1 start -----");
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();
        System.out.println("test1 end -----");
    }

    public void test2() {
        System.out.println("test2 start -----");
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe(i -> System.out.println(i));
        System.out.println("test2 end -----");
    }

    public void test3() {
        System.out.println("test3 start -----");
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error : " + error));
        System.out.println("test3 end -----");
    }

    // https://godekdls.github.io/Reactor%20Core/reactorcorefeatures/    4.3.1 학습
    public void test4() {
        System.out.println("test4 start -----");
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),                      // 데이터 처리
                error -> System.err.println("Error : " + error),        // 에러처리
                () -> System.out.println("Done"));                      // 특정 코드 실행
        System.out.println("test4 end -----");
    }

}
