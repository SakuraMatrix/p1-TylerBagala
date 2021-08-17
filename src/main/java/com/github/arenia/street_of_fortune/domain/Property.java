package com.github.arenia.street_of_fortune.domain;

import java.util.Objects;

public class Property {
    int id;
    String name;
    double value;
    double max_investment;
    double shop_price;
    String owner;
    String district_name;
    double stock_value;

    public Property(){}

    public Property(int id, String name, double value, double max_investment, double shop_price, String owner, String district_name, double stock_value){
        this.id = id;
        this.name = name;
        this.value = value;
        this.max_investment = max_investment;
        this.shop_price = shop_price;
        this.owner = owner;
        this.district_name = district_name;
        this.stock_value = stock_value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return id == property.id
        && name.equals(property.name)
        && Double.compare(property.value, value) == 0
        && Double.compare(property.max_investment, max_investment) == 0
        && Double.compare(property.shop_price, shop_price) == 0
        && owner.equals(property.owner)
        && district_name.equals(property.district_name)
        && Double.compare(property.stock_value, stock_value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, max_investment, shop_price, owner, district_name, stock_value);
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

    public double getMaxInvestment(){
        return max_investment;
    }

    public double getShopPrice(){
        return shop_price;
    }

    public String getOwner(){
        return owner;
    }

    public String getDistrictName(){
        return district_name;
    }

    public double getStockValue(){
        return stock_value;
    }
}
