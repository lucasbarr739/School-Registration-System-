package project2;

/**
 * International class for all international students
 * @author Lucas Barrales
 */
public class International extends NonResident {

    private final boolean studyAbroad;

    private static final double ADMIN_FEE = 500;
    private static final double HEALTH_INSURANCE = 2650;

    public International(Profile profile, Major major, int credits, boolean studyAbroad) {
        super(profile, major, credits);
        this.studyAbroad = studyAbroad;
    }

    public boolean isStudyAbroad() {
        return studyAbroad;
    }

    @Override
    public String getStudentTypeTag() {
        return studyAbroad ? "International study abroad" : "International";
    }

    @Override
    public double tuition(int creditsEnrolled) {
        if (studyAbroad) {
            return UNIVERSITY_FEE + ADMIN_FEE + HEALTH_INSURANCE;
        }
        return super.tuition(creditsEnrolled) + ADMIN_FEE + HEALTH_INSURANCE;
    }
}