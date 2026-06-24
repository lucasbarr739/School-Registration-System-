package project2;

import util.Date;

/**
 * Class used to define Student Profiles
 * Includes firstName, lastName, &amp; date of birth
 * @author Lucas Barrales
 */
public class Profile implements Comparable<Profile> {

    //Instance variable that store profile information
    private String firstName;
    private String lastName;
    private Date dob;


    /**
     * Constructor method
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     */
    public Profile(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dateOfBirth;
    }

    /**
     * Getter method for first name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter method for Last name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter method for Date of Birth
     * @return
     */
    public Date getDateOfBirth() {
        return dob;
    }

    /**
     * Setter method
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter method
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter method
     * @param dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dob = dateOfBirth;
    }

    /**
     * Defines how a project2.Profile object prints
     * @return Returns a string in the format "firstName lastName dob"
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " " + dob;
    }

    /**
     * Checks if two profile objects are equal
     * @param other   the reference object with which to compare.
     * @return True if first name, last name, and date of birth same for both, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Profile)) return false;
        Profile o = (Profile) other;
        return this.firstName.equalsIgnoreCase(o.firstName)
                && this.lastName.equalsIgnoreCase(o.lastName)
                && this.dob.equals(o.dob);
    }

    /**
     * Compares two project2.Profile Objects, first by lastname, then firstname, then date of birth
     * @param other the object to be compared.
     * @return -1 if this.profile comes first, 1 if other comes first, 0 if equal
     */
    @Override
    public int compareTo(Profile other) {
        int c = this.lastName.compareToIgnoreCase(other.lastName);
        if (c != 0) return c;

        c = this.firstName.compareToIgnoreCase(other.firstName);
        if(c != 0) return c;

        return this.dob.compareTo(other.dob);
    }


    public static void main(String[] args){
        Profile student1 = new Profile("Otto", "Octavious", new Date(1, 10, 1931));
        Profile student2 = new Profile("Peter", "Parker", new Date(5, 10, 1931));
        System.out.println(student1.compareTo(student2)); // Test Case 1
        student1 = new Profile("Otto", "Parker", new Date(1, 10, 1931));
        student2 = new Profile("Peter", "Parker", new Date(5, 10, 1931));
        System.out.println(student1.compareTo(student2)); // Test Case 2
        student1 = new Profile("Peter", "Parker", new Date(1, 10, 1931));
        student2 = new Profile("Peter", "Parker", new Date(1, 11, 1931));
        System.out.println(student1.compareTo(student2)); // Test Case 3
        student2 = student1;
        System.out.println(student1.compareTo(student2)); // Test Case 4
        student1 = new Profile("Peter", "Parker", new Date(5, 10, 1931));
        student2 = new Profile("Otto", "Octavious", new Date(1, 10, 1931));
        System.out.println(student1.compareTo(student2)); // Test Case 5
        student1 = new Profile("Peter", "Parker", new Date(5, 10, 1931));
        student2 = new Profile("Otto", "Parker", new Date(1, 10, 1931));
        System.out.println(student1.compareTo(student2)); // Test Case 6
        student1 = new Profile("Peter", "Parker", new Date(1, 11, 1931));
        student2 = new Profile("Peter", "Parker", new Date(1, 10, 1931));
        System.out.println(student1.compareTo(student2)); // Test Case 7

    }

}
