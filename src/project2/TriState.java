package project2;

/**
 * Class for tri state students
 * @author Shivang Patel
 */
public class TriState extends NonResident {

    private final String state;

    public TriState(Profile profile, Major major, int credits, String state) {
        super(profile, major, credits);
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String getStudentTypeTag() {
        return "Tristate: " + state;   // expected: Tristate: NY / Tristate: CT
    }

    @Override
    public double tuition(int creditsEnrolled) {
        boolean isFullTime = creditsEnrolled >= 12;

        int remission = 0;
        if ("NY".equals(state)) remission = 4000;
        else if ("CT".equals(state)) remission = 5000;

        if (isFullTime) {
            if (creditsEnrolled > 16) {
                return getFullTimeTuition() + getUniversityFee()
                        + getPerCreditHour() * (creditsEnrolled - 16) - remission;
            }
            return getFullTimeTuition() + getUniversityFee() - remission;
        }

        return getPerCreditHour() * creditsEnrolled + getPartTimeUniversityFee();
    }
}