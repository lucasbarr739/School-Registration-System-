package project2;

/**
 * Defines a Resident Student
 * @author Shivang Patel
 */
public class Resident extends Student {

    private int scholarship; // dollars

    private static final double PER_CREDIT_HOUR = 482;
    private static final double FULL_TIME_TUITION = 14933;
    private static final double UNIVERSITY_FEE = 3891;
    private static final double PART_TIME_UNIVERSITY_FEE = 0.5 * UNIVERSITY_FEE;

    public Resident(Profile profile, Major major, int credits) {
        super(profile, major, credits);
        this.scholarship = 0;
    }

    public Resident(Profile profile, Major major, int credits, int scholarship) {
        super(profile, major, credits);
        this.scholarship = Math.max(0, scholarship);
    }

    /**
     * Tuition method for Resident students
     * Rules:
     * - Part-time (&lt;12): per-credit + half university fee; scholarship does NOT apply
     * - Full-time (>=12): full tuition + university fee (+ extra per-credit beyond 16) - scholarship
     */
    @Override
    public double tuition(int creditsEnrolled) {
        if (creditsEnrolled < 12) {
            return PER_CREDIT_HOUR * creditsEnrolled + PART_TIME_UNIVERSITY_FEE;
        }

        double total = FULL_TIME_TUITION + UNIVERSITY_FEE;

        if (creditsEnrolled > 16) {
            total += PER_CREDIT_HOUR * (creditsEnrolled - 16);
        }

        total -= scholarship;
        if (total < 0) total = 0;
        return total;
    }

    public void setScholarship(int amount) {
        this.scholarship = Math.max(0, amount);
    }

    public int getScholarship() {
        return scholarship;
    }

    /** Needed for PS output like: [resident] [scholarship: $8,000] */
    @Override
    public String getStudentTypeTag() {
        if (scholarship > 0) {
            return "resident] [scholarship: $" + formatMoneyInt(scholarship);
        }
        return "resident";
    }

    private String formatMoneyInt(int dollars) {
        return String.format("%,d", dollars);
    }
}