package util;
/**
 * Defines dates based on integers representing month, day, and year
 * Months are defined 1-12
 * @author Lucas Barrales
 */

import java.util.Calendar;
public class Date implements Comparable<Date>{
    // Instance variables store the state of the object
    private int month;
    private int day;
    private int year;

    /**
     * Constructor method
     * @param month
     * @param day
     * @param year
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Getter method
     * @return this.month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter method
     * @return this.day
     */
    public int getDay() {
        return day;
    }

    /**
     * Getter method
     * @return this.year
     */
    public int getYear(){
        return year;
    }

    /**
     * Setter method
     * @param month
     */
    public void setMonth(int  month){
        this.month = month;
    }

    /**
     * Setter method
     * @param day
     */
    public void setDay(int day){
        this.day = day;
    }

    /**
     * Setter method
     * @param year
     */
    public void setYear(int year){
        this.year = year;
    }

    /**
     * Defines how date object is printed with toString() method
     * @return project1.Date in format MM/DD/YYYY
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Compares two date objects and checks if they're equal
     * @param other project1.Date object to compare
     * @return True if years, months, and days are same, false otherwise
     */
    @Override
    public boolean equals (Object other) {
        if (!(other instanceof Date)){
            return false;
        }
        Date otherDate = (Date) other;
        return this.month == otherDate.month &&
                this.day == otherDate.day &&
                this.year == otherDate.year;
    }

    /**
     * Compares two date objects
     * @param o the object to be compared.
     * @return -1 if this.date comes before o.date, 1 if o.date comes before, 0 otherwise
     */
    @Override
    public int compareTo(Date o) {
        if (this.equals(o)){ // if the same day
            return 0;
        }
        if  (this.getYear() < o.getYear() || // if this.year comes before o.year
                (this.getYear() == o.getYear() && this.getMonth() < o.getMonth()) || // if years are same, but month is earlier
                (this.getYear() == o.getYear() && this.getMonth() == o.getMonth() && this.getDay() < o.getDay())){ // if years & months are same, but day is earlier
            return -1;
        }
        return 1; // if not the same day and this does not come first, o must come first, thus 1.
    }

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    // months are 0-11 in Calendar, add 1 to make 1-12
    public static final int JAN = Calendar.JANUARY + 1; // 1
    public static final int FEB = Calendar.FEBRUARY + 1; // 2
    public static final int MAR = Calendar.MARCH + 1; // 3
    public static final int APR = Calendar.APRIL + 1; // 4
    public static final int MAY = Calendar.MAY + 1; // 5
    public static final int JUN = Calendar.JUNE + 1; // 6
    public static final int JUL = Calendar.JULY + 1; // 7
    public static final int AUG = Calendar.AUGUST + 1; // 8
    public static final int SEP = Calendar.SEPTEMBER + 1; // 9
    public static final int OCT = Calendar.OCTOBER + 1; // 10
    public static final int NOV = Calendar.NOVEMBER + 1; // 11
    public static final int DEC = Calendar.DECEMBER + 1; // 12

    int[] months31Days = {JAN, MAR, MAY, JUL, AUG, OCT, DEC};
    int[] months30Days = {APR, JUN, SEP, NOV};

    /**
     * Checks if a date is valid
     * @return True if date is valid, false otherwise
     */
    public boolean isValid(){
        int day = this.getDay();
        int month = this.getMonth();
        int year = this.getYear();
        if (month < 1 || month > 12 || day < 1){ // month out of 1-12 or day is 0 or negative
            return false;
        }
        for (int monthWith31Days : months31Days){
            if (monthWith31Days == month && day > 31) {
                return false;
            }
        } // if month is a month with 31 days
        for (int monthWith30Days : months30Days){
            if (monthWith30Days == month && day > 30) {
                return false;
            }
        } // if month is a month with 30 days
        boolean isLeap =    (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) ||
                (year % CENTENNIAL == 0 && year % QUATERCENTENNIAL == 0);
        if (month == 2){
            if (isLeap && day > 29){
                return false;
            }
            else if (!isLeap && day > 28){
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if date is today or in the future
     * @return True if dob == today OR dob after today
     */
    public boolean isTodayOrFuture() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar dob = Calendar.getInstance();
        dob.set(Calendar.YEAR, this.year);
        dob.set(Calendar.MONTH, this.month - 1);
        dob.set(Calendar.DAY_OF_MONTH, this.day);
        dob.set(Calendar.HOUR_OF_DAY, 0);
        dob.set(Calendar.MINUTE, 0);
        dob.set(Calendar.SECOND, 0);
        dob.set(Calendar.MILLISECOND, 0);

        return !dob.before(today); // true if dob == today OR dob after today
    }

    /**
     * Checks if the age is valid
     * @return True if age is valid; False if age is invalid
     */
    public boolean isAtLeast16() {
        Calendar today = Calendar.getInstance();

        Calendar dob = Calendar.getInstance();
        dob.set(Calendar.YEAR, this.year);
        dob.set(Calendar.MONTH, this.month - 1); // Calendar months are 0-based
        dob.set(Calendar.DAY_OF_MONTH, this.day);

        if (dob.after(today)) return false;

        Calendar sixteen = (Calendar) dob.clone();
        sixteen.add(Calendar.YEAR, 16);

        // If today is on/after the 16th birthday, they are at least 16
        return !today.before(sixteen);
    }


    public static void main(String[] args){
        Date invalid1 = new Date(2, 29, 2003); // Feb 29th, 2003, should be invalid
        Date invalid2 = new Date(3, -1, 2004); // Mar -1st, 2004, should be invalid
        Date invalid3 = new Date(-1, 1, 2005); // the 1st of the -1st month, 2005, should be invalid
        Date invalid4 = new Date(13, 4, 2006); // the 4th of the 13th month, 2006, should be invalid
        Date valid1 = new Date(2, 29, 2004); // Feb 29th, 2004, should be valid
        Date valid2 = new Date(10, 31, 2005); // October 31st, 2005, should be valid
        System.out.println(invalid1.isValid()); // Test Case 1
        System.out.println(invalid2.isValid()); // Test Case 2
        System.out.println(invalid3.isValid()); // Test Case 3
        System.out.println(invalid4.isValid()); // Test Case 4
        System.out.println(valid1.isValid()); // Test Case 5
        System.out.println(valid2.isValid()); // Test Case 6
    }

}