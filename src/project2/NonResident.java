package project2;

/**
 * Defines a NonResident student (base for TriState and International).
 * This class should be abstract per project spec, but it CAN still implement tuition().
 * @author Lucas Barrales
 */
public abstract class NonResident extends Student {

    protected static final double PER_CREDIT_HOUR = 1162;
    protected static final double FULL_TIME_TUITION = 35758;
    protected static final double UNIVERSITY_FEE = 3891;
    protected static final double PART_TIME_UNIVERSITY_FEE = 0.5 * UNIVERSITY_FEE;

    public NonResident(Profile profile, Major major, int credits) {
        super(profile, major, credits);
    }

    @Override
    public double tuition(int creditsEnrolled) {
        if (creditsEnrolled >= 12) {
            double total = FULL_TIME_TUITION + UNIVERSITY_FEE;
            if (creditsEnrolled > 16) {
                total += PER_CREDIT_HOUR * (creditsEnrolled - 16);
            }
            return total;
        }
        return PER_CREDIT_HOUR * creditsEnrolled + PART_TIME_UNIVERSITY_FEE;
    }

    // Keep these helpers because your TriState uses them
    public double getFullTimeTuition() { return FULL_TIME_TUITION; }
    public double getUniversityFee() { return UNIVERSITY_FEE; }
    public double getPerCreditHour() { return PER_CREDIT_HOUR; }
    public double getPartTimeUniversityFee() { return PART_TIME_UNIVERSITY_FEE; }
}