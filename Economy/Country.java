package Economy;

import java.text.DecimalFormat;

public class Country{

    private String name;
    private int population;
    private double GDP;


    public Country(String name, int population, double GDP) {
        this.name = name;
        this.population = population;
        this.GDP = GDP;
    }

    @Override
    public String toString() {
        final String END_LINE = System.lineSeparator();

        return END_LINE+this.name + ": ("+getClassification()+") "+END_LINE
        + "Population: " + this.population+END_LINE
        + "GDP: " + new DecimalFormat("#").format(GDP)+ END_LINE
        + new DecimalFormat("#").format(getGDPCapita()) + " per capita!" +END_LINE;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        if(population < 0){
            return;
        }
        this.population = population;
    }

    public double getGDP() {
        return GDP;
    }

    public void setGDP(double GDP) {
        if(GDP < 0){
            return;
        }
        this.GDP = GDP;
    }

    public double getGDPCapita() {

        return GDP/population;

    }

    public String getClassification() {
        return this.getGDPCapita() > 1000 ?
                (this.getGDPCapita() >= 10000 ? "Developed Country" : "Economy in transition") : "Developing country";
    }



}
