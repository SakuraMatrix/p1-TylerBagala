package com.github.arenia.street_of_fortune.domain;

import java.util.Objects;

public class Land {
    int id;
    String name;
    double value;
    double shop_price;

    public Land(){}

    public Land(int id, String name, double value, double shop_price){
        this.id = id;
        this.name = name;
        this.value = value;
        this.shop_price = shop_price;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Land property = (Land) o;
        return id == property.id
        && name.equals(property.name)
        && Double.compare(property.value, value) == 0
        && Double.compare(property.shop_price, shop_price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, shop_price);
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getValue(){
        return value;
    }

    public double getShopPrice(){
        return shop_price;
    }
}
