package project2;

/**
 * Enum Class for Classrooms, has two additional properties - Building and Campus
 * @author Shivang Patel
 */
public enum Classroom {
    HIL114("Hill Center", "Busch"),
    ARC103("Allison Road Classroom", "Busch"),
    BEAUD("Beck Hall", "Livingston"),
    TIL232("Tillett Hall", "Livingston"),
    AB2225("Academic Building", "College Avenue"),
    MU302("Murray Hall", "College Avenue");

    private final String building;
    private final String campus;

    /**
     * Constructor method used by JVM to create classroom objects defined above
     *
     * @param building Name of the building the classroom is located
     * @param campus   Name of the campus the classroom is located
     */
    Classroom(String building, String campus) {
        this.building = building;
        this.campus = campus;
    }

    /**
     * toString() override function
     *
     * @return Returns a string of the classroom name, building the clasroom is located, and campus the classroom is located
     */
    @Override
    public String toString() {
        return name() + ", " + building + ", " + campus;
    }

    public String getCampus() {
        return campus;
    }

    public String getBuilding() {
        return building;
    }



}
