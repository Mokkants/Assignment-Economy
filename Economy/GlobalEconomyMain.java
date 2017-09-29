package Economy;
import java.util.Scanner;

/**
* Created by mokkants on 2017-09-20.
*/

//you may need to change the package to match your project's package.

public class GlobalEconomyMain {

    private static final int REGISTER_COUNTRY = 1;
    private static final int PRINT_COUNTRIES = 2;
    private static final int PRINT_COUNTRY = 3;
    private static final int INJECT_MONEY = 4;
    private static final int PAY_DEBT = 5;
    private static final int QUIT = 6;
    private static final int EDIT_COUNTRY_INFO = 7;

    private final String END_LINE = System.lineSeparator();

    private Country[] countries;
    private int registeredCountries;
    private Scanner input;

    /*
     * Constructor to initialize your data set of countries.
     * It begins empty. The default size is 5 countries.
     */
    public GlobalEconomyMain() {
        final int MAX_COUNTRIES = 5;
        this.countries = new Country[MAX_COUNTRIES];
        this.registeredCountries = 0;
        input = new Scanner(System.in);
    }

    /*
     * This method will retrieve a country from the array based on a specified name.
     * If the country was not created and added to the array, it will return NULL,
     * meaning that the country does not exist in the system.
     *
     */
    public Country retrieveCountry(String countryName) {
        for (int i = 0; i < this.countries.length; i++) {
            //ask yourself, why do I check null first?
            if(countries[i] != null && countries[i].getName().equals(countryName)) {
                //I found the country, return it then.
                return countries[i];
            }
        }

        //example of a null... if no country
        //   with the give name exists, the
        //   object does not exist (i.e. "nothing").
        return null;
    }

    public Country createCountry() {

        System.out.println("What is the name of the country?");
        String countryName = input.nextLine();

        if (retrieveCountry(countryName) !=  null){
            System.out.println("Country already registered!"+END_LINE);
            return null;
        }

        System.out.println("How big is the population?");
        int countryPop = input.nextInt();
        input.nextLine();

        if (countryPop < 0){
            System.out.println("Population cannot be negative!"+END_LINE);
            return null;
        }

        System.out.println("And how big is the GDP?");
        double countryGDP = input.nextDouble();
        input.nextLine();

        if (countryGDP < 0){
            System.out.println("GDP cannot be negative!"+END_LINE);
            return null;
        }

        System.out.println(END_LINE+"Thank you!");
        return new Country(countryName,countryPop,countryGDP);

    }

    public void run() {

        int option;
        do {
            printMenuOptions();
            System.out.print(" Type the option number: ");

            option = input.nextInt();
            input.nextLine(); //this skips the enter
            //that the user types after
            //typing the integer option.

            switch (option) {
                case REGISTER_COUNTRY:

                    Country newCountry = createCountry();
                    if(newCountry != null){
                        this.countries[registeredCountries] = newCountry;
                        this.registeredCountries = this.registeredCountries + 1;
                    }

                    break;

                case PRINT_COUNTRIES:
                    printAllCountries();
                    break;

                case PRINT_COUNTRY:
                    printOneCountry();
                    break;

                case INJECT_MONEY:
                    injectMoney();
                    break;

                case PAY_DEBT:
                    payDebt();
                    break;

                case QUIT:
                    System.out.println("Thank you for using Global Economy Solutions. See you soon!");
                    System.out.println();
                    break;

                case EDIT_COUNTRY_INFO:
                    editCountryInfo();
                    break;

                default:
                    System.out.println("Option "+option+" is not valid.");
                    System.out.println();
                    break;
            }
        } while (option != QUIT);
    }


    //This method is private because it should be used only by
    // this class since the menu is specific to this main.
    private void printMenuOptions() {
        System.out.println(" === Welcome to Global Economy === " +END_LINE
        +" Choose an option below: " +END_LINE
        + END_LINE
        +" 1. Register a country. " +END_LINE
        +" 2. Print all countries. " +END_LINE
        +" 3. Print a country's information. " +END_LINE
        +" 4. Inject money to a country. " +END_LINE
        +" 5. Pay a country's debt. " +END_LINE
        +" 6. Quit this program. " +END_LINE
        +" 7. Edit country Information. ");
    }
    public void printAllCountries() {

        for (int i = 0; i < 5; i++) {
            if (countries[i] != null) {
                System.out.println(countries[i]);

            }
        }

    }

    public void printOneCountry() {
        String countryName = readCountryName();

        Country foundCountry = retrieveCountry(countryName);

        if(foundCountry == null){
            System.out.println(END_LINE+"Error: "+countryName+" is not registered."+END_LINE);
            return;
        }

        System.out.println(foundCountry.toString());
    }

    /*
     * This method only reads a String that here, will be the name
     * of a country that you want to use
     * (for printing, injecting money, paying debt, etc.)
     */
    public String readCountryName() {
        System.out.print("Type the name of the country that you want to use: ");
        String countryName = input.nextLine();
        return countryName;
    }

    public void injectMoney() {
        String countryName = readCountryName();
        Country foundCountry = retrieveCountry(countryName);

        if (foundCountry == null){
            System.out.println("Error: "+countryName+" is not registered."+END_LINE);
            return;
        }

        System.out.println("Specify the amount: ");
        double amount = input.nextDouble();

        if (amount<=0){
            System.out.println("Error when injecting money in "+countryName+". Amount has to be higher than zero");
            return;
        }

        foundCountry.setGDP(foundCountry.getGDP()+amount);

        System.out.println(countryName+" new GDP is "+foundCountry.getGDP()+END_LINE);

    }

    public void payDebt() {
        String countryName = readCountryName();
        Country foundCountry = retrieveCountry(countryName);

        if (foundCountry == null){
            System.out.println("Error: "+countryName+" is not registered."+END_LINE);
            return;
        }

        System.out.println("Specify the amount: ");
        double amount = input.nextDouble();

        if (amount<=0){
            System.out.println("Error when injecting money in "+countryName+". Amount has to be higher than zero");
            return;
        }

        if(foundCountry.getGDP()-amount < 0){
            System.out.println("Failed to deduct amount, country doesn't have enough GDP");
        }
        foundCountry.setGDP(foundCountry.getGDP()-amount);

        System.out.println(countryName+" new GDP is "+foundCountry.getGDP() +END_LINE);

    }

    private void editCountryInfo() {

        String countryName = readCountryName();
        Country foundCountry = retrieveCountry(countryName);

        if (foundCountry == null){
            System.out.println("Error: "+countryName+" is not registered."+END_LINE);
            return;
        }


        System.out.println("1. Change country's name");
        System.out.println("2. Change country's population");

        int option;
        final int EDIT_COUNTRY_NAME = 1;
        final int EDIT_POPULATION = 2;

        do{
            option = input.nextInt();
            input.nextLine();
            if(option!= EDIT_COUNTRY_NAME && option != EDIT_POPULATION){
                System.out.println("That's not a valid option. Try again!");
            }
        }while(option != EDIT_COUNTRY_NAME && option != EDIT_POPULATION);

        switch (option){
            case EDIT_COUNTRY_NAME:
                System.out.println("Enter the country's new name!");
                String name = input.nextLine();
                foundCountry.setName(name);
                break;

            case EDIT_POPULATION:
                System.out.println("Enter the country's new population!");
                int population = input.nextInt();
                foundCountry.setPopulation(population);
                break;
        }

    }


    public static void main(String[] args) {
        GlobalEconomyMain program = new GlobalEconomyMain();
        program.run();
    }
}


