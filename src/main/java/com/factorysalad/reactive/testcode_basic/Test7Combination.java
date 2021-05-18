package com.factorysalad.reactive.testcode_basic;

import com.factorysalad.reactive.ReactiveApplication;
import com.factorysalad.reactive.model.Person;
import com.factorysalad.reactive.model.Sale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Test7Combination {

    private static final Logger log = LoggerFactory.getLogger(ReactiveApplication.class);

    public static void main(String[] args) {
        Test7Combination t7 = new Test7Combination();
        t7.merge();
        t7.zip();
        t7.zipWith();
    }

    public void merge() {
        System.out.println("merge start -----");
        List<Person> personList1 = new ArrayList<>();
        personList1.add(new Person(1, "강감찬", 19));
        personList1.add(new Person(2, "홍길동", 23));
        personList1.add(new Person(3, "이순신", 30));

        List<Person> personList2 = new ArrayList<>();
        personList2.add(new Person(4, "을지문덕", 42));
        personList2.add(new Person(5, "이황", 52));
        personList2.add(new Person(6, "이이", 61));

        List<Sale> saleList = new ArrayList<>();
        saleList.add(new Sale(1, LocalDateTime.now()));
        saleList.add(new Sale(2, LocalDateTime.now()));

        Flux<Person> fx1 = Flux.fromIterable(personList1);
        Flux<Person> fx2 = Flux.fromIterable(personList2);
        Flux<Sale> fx3 = Flux.fromIterable(saleList);

        Flux.merge(fx1, fx2, fx3, fx1)
                .subscribe(p -> log.info(p.toString()));
        System.out.println("merge end -----");
        /*
        결과 :
        Person{idPerson=1, names='강감찬', age=19}
        Person{idPerson=2, names='홍길동', age=23}
        Person{idPerson=3, names='이순신', age=30}
        Person{idPerson=4, names='을지문덕', age=42}
        Person{idPerson=5, names='이황', age=52}
        Person{idPerson=6, names='이이', age=61}
        Sale{saleId=1, date=2021-05-18T10:13:28.487039300}
        Sale{saleId=2, date=2021-05-18T10:13:28.487039300}
        Person{idPerson=1, names='강감찬', age=19}
        Person{idPerson=2, names='홍길동', age=23}
        Person{idPerson=3, names='이순신', age=30}
         */
    }

    public void zip() {
        List<Person> personList1 = new ArrayList<>();
        personList1.add(new Person(1, "강감찬", 19));
        personList1.add(new Person(2, "홍길동", 23));
        personList1.add(new Person(3, "이순신", 30));

        List<Person> personList2 = new ArrayList<>();
        personList2.add(new Person(4, "을지문덕", 42));
        personList2.add(new Person(5, "이황", 52));
        personList2.add(new Person(6, "이이", 61));

        List<Sale> saleList = new ArrayList<>();
        saleList.add(new Sale(1, LocalDateTime.now()));
        saleList.add(new Sale(2, LocalDateTime.now()));

        Flux<Person> fx1 = Flux.fromIterable(personList1);
        Flux<Person> fx2 = Flux.fromIterable(personList2);
        Flux<Sale> fx3 = Flux.fromIterable(saleList);

        System.out.println("zip1 start -----");
        // stream fx1과 fx2를 결합하고 각각의 stream에서 Person을 하나씩 뽑아서 처리한다.
        Flux.zip(fx1, fx2, (p1, p2) -> String.format("Flux1: %s, Flux2: %s", p1, p2))
                .subscribe(log::info);
        System.out.println("zip1 end -----");
        /*
        결과 :
        Flux1: Person{idPerson=1, names='강감찬', age=19}, Flux2: Person{idPerson=4, names='을지문덕', age=42}
        Flux1: Person{idPerson=2, names='홍길동', age=23}, Flux2: Person{idPerson=5, names='이황', age=52}
        Flux1: Person{idPerson=3, names='이순신', age=30}, Flux2: Person{idPerson=6, names='이이', age=61}
         */

        System.out.println("zip2 start -----");
        // stream fx1, fx2, fx3을 그룹화한다. 그룹화 시킬수 없는 것은 제외한다.
        Flux.zip(fx1, fx2, fx3)
                .subscribe(x -> log.info(x.toString()));
        System.out.println("zip2 end -----");
        /*
        결과 :
        [Person{idPerson=1, names='강감찬', age=19},Person{idPerson=4, names='을지문덕', age=42},Sale{saleId=1, date=2021-05-18T10:13:28.766842100}]
        [Person{idPerson=2, names='홍길동', age=23},Person{idPerson=5, names='이황', age=52},Sale{saleId=2, date=2021-05-18T10:13:28.766842100}]
         */
    }

    public void zipWith() {
        System.out.println("zipWith start -----");
        List<Person> personList1 = new ArrayList<>();
        personList1.add(new Person(1, "강감찬", 19));
        personList1.add(new Person(2, "홍길동", 23));
        personList1.add(new Person(3, "이순신", 30));

        List<Sale> saleList = new ArrayList<>();
        saleList.add(new Sale(1, LocalDateTime.now()));
        saleList.add(new Sale(2, LocalDateTime.now()));

        Flux<Person> fx1 = Flux.fromIterable(personList1);
        Flux<Sale> fx3 = Flux.fromIterable(saleList);

        // Flux 인스턴스를 만드는 대신 다른 Flux와 연결하여 수행한다.
        fx1.zipWith(fx3, (p1, v1) -> String.format("Flux1: %s, Flux2: %s", p1, v1))
                .subscribe(log::info);
        System.out.println("zipWith end -----");
        /*
        Flux1: Person{idPerson=1, names='강감찬', age=19}, Flux2: Sale{saleId=1, date=2021-05-18T10:22:22.663493700}
        Flux1: Person{idPerson=2, names='홍길동', age=23}, Flux2: Sale{saleId=2, date=2021-05-18T10:22:22.663493700}
         */
    }
}
