package com.github.arenia.street_of_fortune.domain;

import java.util.Objects;

public class Investor {
    int id;
    String name;
    double netWorth;

    public Investor(){}

    public Investor(int id, String name, double netWorth){
        this.id = id;
        this.name = name;
        this.netWorth = netWorth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investor investor = (Investor) o;
        return id == investor.id
        && Double.compare(investor.netWorth, netWorth) == 0 
        && Objects.equals(name, investor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, netWorth);
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getNetWorth(){
        return netWorth;
    }
}
