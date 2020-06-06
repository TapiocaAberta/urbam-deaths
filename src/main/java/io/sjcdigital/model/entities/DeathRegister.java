package io.sjcdigital.model.entities;

/**
 * Holds a single death register without person details.
 *
 */
public class DeathRegister {

    public static final String NO_FUNERAL = "DIRETO";
    private String month;
    private String year;
    private String funeralType;

    public DeathRegister(String month, String year, String funeralType) {
        super();
        this.month = month;
        this.year = year;
        this.funeralType = funeralType;
    }

    public static DeathRegister from(Person p) {
        String burialType = p.getFuneral().equalsIgnoreCase(NO_FUNERAL) ? NO_FUNERAL : "OUTRO";
        return new DeathRegister(p.getMonthDeath(), p.getYearDeath(), burialType);
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getFuneralType() {
        return funeralType;
    }

}