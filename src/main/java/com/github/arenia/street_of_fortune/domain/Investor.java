package com.github.arenia.street_of_fortune.domain;

import java.util.Objects;

public class Investor {
    int id;
    String name;
    double net_worth;

    public Investor(){}

    public Investor(int id, String name, double net_worth){
        this.id = id;
        this.name = name;
        this.net_worth = net_worth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return id == investor.id
        && Double.compare(investor.net_worth, net_worth) == 0 
        && Objects.equals(name, investor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, net_worth);
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getNetWorth(){
        return net_worth;
    }
}
