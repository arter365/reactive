package com.factorysalad.reactive.model;

import java.util.Objects;

public class Person2 {
    private Integer idPerson;
    private String names;
    private Integer age;

    public Person2(Integer idPerson, String names, Integer age) {
        this.idPerson = idPerson;
        this.names = names;
        this.age = age;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "idPerson=" + idPerson +
                ", names='" + names + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person2 person2 = (Person2) o;
        return idPerson.equals(person2.idPerson) && names.equals(person2.names) && age.equals(person2.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerson, names, age);
    }
}
