package project2;

/**
 * Enum class for Instructors, has no additional properties
 * @author Shivang Patel
 */
public enum Instructor {
    PATEL,
    LIM,
    ZIMNES,
    HARPER,
    KAUR,
    TAYLOR,
    RAMESH,
    CERAVOLO,
    BROWN;

    /**
     * Constructor called by JVM to create instructor objects defined above.
     */
    Instructor(){
    }

    /**
     * toString() override function
     * @return Returns last name of instructor
     */
    @Override
    public String toString() {
        return name();
    }

}