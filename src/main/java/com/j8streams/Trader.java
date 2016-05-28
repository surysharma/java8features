package com.j8streams;

/**
 * Created by sureshsharma on 27/05/2016.
 */
public class Trader {
    private final String name;
    private final String city;

    public Trader(String name, String city){

        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trader trader = (Trader) o;

        if (name != null ? !name.equals(trader.name) : trader.name != null) return false;
        return city != null ? city.equals(trader.city) : trader.city == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
