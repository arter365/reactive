package com.factorysalad.reactive.model;

import java.time.LocalDateTime;

public class Sale {
    private Integer saleId;
    private LocalDateTime date;

    public Sale(Integer saleId, LocalDateTime date) {
        this.saleId = saleId;
        this.date = date;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", date=" + date +
                '}';
    }
}
