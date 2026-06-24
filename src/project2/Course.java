package project2;

/**
 * Enum Class for Courses, includes additional properties: Credit Hours, Standing &amp; Major
 * @author Shivang Patel
 */
public enum Course {
    CS100 (4, "Freshman"),
    CS200 (4, "Sophomore"),
    CS300 (4, "Junior", "CS major only"),
    CS400 (4, "Junior", "CS major only"),
    CS442 (3, "Junior"),
    PHY100 (5, "Freshman"),
    PHY200 (5, "Freshman"),
    ECE300 (4, "Junior", "ECE major only"),
    ECE400 (4, "Senior", "ECE major only"),
    CCD (4, "Freshman"),
    HST (3, "Freshman");



    private final int creditHours;
    private final String standing;
    private final String reqMajor;

    /**
     Constructor used by the JVM to create courses defined above that have an additional required major
     @param creditHours - an additional property to identify the credit hours of a course
     @param standing - an additional property to identify the year standing required for a course
     @param reqMajor - an additional property to identify an extra major requirement for a course
     */
    Course(int creditHours, String standing, String reqMajor){
        this.creditHours = creditHours;
        this.standing = standing;
        this.reqMajor = reqMajor;
    }

    /**
     Constructor used by the JVM to create courses defined above
     @param creditHours - an additional property to identify the credit hours of a course
     @param standing - an additional property to identify the year standing required for a course
     */
    Course(int creditHours, String standing){
        this.creditHours = creditHours;
        this.standing = standing;
        this.reqMajor = null;
    }

    /**
     * Helper Method - Returns true if there is an extra requirement for a course, false otherwise
     * @return
     */
    private boolean extraRequirement(){
        if (this.reqMajor == null){
            return false;
        }
        return true;
    }


    /**
     * Getter method for Credit Hours
     * @return project2.Course's Credit Hours
     */
    public int getCreditHours(){return this.creditHours;}

    /**
     * Getter method for Standing
     * @return project2.Course's Required Standing
     */
    public String getStanding(){return this.standing;}

    /**
     * Getter method for ReqMajor, checks to make sure it exists before returning
     * @return project2.Course's Required Major, null if no required major
     */
    public String getReqMajor(){return this.reqMajor;}

    /**
     * Overrides toString() method, returns course designation, credit hours, and standing (and requird major if exists)
     * @return CourseName, creditHours, standing(, reqMajor if course has a required major)
     */
    @Override
    public String toString(){
        if (this.extraRequirement()){
            return name() + ", " + creditHours + ", " + standing + ", " + reqMajor;
        }
        return name() + ", " + creditHours + ", " + standing;
    }

}