package project2;

/**
 * Enum class for Majors, includes additional properties: School
 * @author Lucas Barrales &amp; Shivang Patel
 */
public enum Major {
    CS ("School of Arts & Sciences"),
    ECE ("School of Engineering"),
    MATH ("School of Arts & Sciences"),
    ITI ("School of Communication and Information"),
    BAIT ("Rutgers Business School");

    private final String school; // The school that hosts the major

    /**
     * Constructor method used by JVM to construct Major objects defined above
     * @param school School that offers said major
     */
    Major(String school){
        this.school = school;
    }

    /**
     * Getter Method for School
     * @return
     */
    public String getSchool(){
        return this.school;
    }

    /**
     * Getter Method for Major as a String
     * @return
     */
    public String getMajor() { return this.name(); }

    /**
     * Overrides toString() method
     * @return Major with the format majorName,schoolName
     */
    @Override
    public String toString(){
        return this.getMajor() + "," + this.getSchool();
    }





}