package project2;

import util.List;
import util.Sort;

/**
 * A list of Student objects backed by util.List.
 * @author Shivang Patel
 */
public class StudentList extends List<Student> {

    /**
     * Find a student by profile (case-insensitive name, exact dob).
     * @param profile profile to match
     * @return matching student or null
     */
    public Student findByProfile(Profile profile) {
        for (int i = 0; i < size(); i++) {
            Student s = get(i);
            if (s.getProfile().equals(profile)) return s;
        }
        return null;
    }

    /**
     * Print student list ordered by profile.
     */
    public void printByProfile() {
        if (isEmpty()) {
            System.out.println("Student list is empty!");
            return;
        }
        System.out.println("* Student list ordered by last, first name, DOB *");

        // Make a shallow copy so sorting doesn't mutate original order
        StudentList copy = new StudentList();
        for (Student s : this) copy.add(s);
        Sort.sortByProfile(copy);

        for (Student s : copy) {
            System.out.println(s.printStudentLine());
        }
        System.out.println("* end of list **");
    }

    /**
     * Print graduation list ordered by major (then profile).
     * A student qualifies if credits earned >= 120.
     */
    public void printGraduationByMajor() {
        StudentList grads = new StudentList();
        for (Student s : this) {
            if (s.getCredits() >= 120) grads.add(s);
        }

        if (grads.isEmpty()) {
            System.out.println("No students are eligible for graduation.");
            return;
        }

        System.out.println("* List of students eligible for graduation, ordered by major *");
        Sort.sortByMajor(grads);

        for (Student s : grads) {
            // Expected PG line format:
            // [Name DOB][MAJOR,School]
            System.out.println("[" + s.getProfile().toString() + "][" +
                    s.getMajor().name() + "," + s.getMajor().getSchool() + "]");
        }

        System.out.println("* end of list *");
    }

    /**
     * Print tuition report ordered by profile.
     * Tuition is computed using currently enrolled credits (from Schedule).
     */
    public void printTuitionReport(Schedule schedule) {
        if (isEmpty()) {
            System.out.println("Student list is empty!");
            return;
        }

        System.out.println("* Tuition dues ordered by student. *");

        // sort students by profile
        StudentList copy = new StudentList();
        for (Student s : this) copy.add(s);
        Sort.sortByProfile(copy);

        for (Student s : copy) {

            // --- student header line like: [Name DOB][Resident] ---
            System.out.println("[" + s.getProfile().toString() + "][" + studentTypeLabel(s) + "]");

            util.List<Section> enrolledSections = schedule.sectionsEnrolled(s);

            // Not enrolled case
            if (enrolledSections.isEmpty()) {
                System.out.println("\t\t**not enrolled.");
                continue;
            }

            // sort sections by course then time
            Sort.sortByCourseThenTime(enrolledSections);

            // print each section line
            for (int i = 0; i < enrolledSections.size(); i++) {
                Section sec = enrolledSections.get(i);
                System.out.println("\t\t" + sec.tuitionLine());
            }

            int totalCredits = schedule.creditsEnrolled(s);

            // International min-12 rule (non-study-abroad only)
            if (s instanceof International intl) {
                if (!intl.isStudyAbroad() && totalCredits < 12) {
                    System.out.println("\t\t**International student must enroll at least 12 credits.");
                    continue;
                }
            }

            double tuition = s.tuition(totalCredits);
            System.out.printf("\t\t**Total credits enrolled: %d [tuition due: $%,.2f]%n",
                    totalCredits, tuition);
        }

        System.out.println("* end of list *");
    }

    private String studentTypeLabel(Student s) {
        if (s instanceof Resident) {
            return "Resident";
        }
        if (s instanceof TriState ts) {
            return "Tristate: " + ts.getState(); // must exist
        }
        if (s instanceof International intl) {
            return intl.isStudyAbroad() ? "International study abroad" : "International";
        }
        // plain nonresident
        return "Noresident"; // matches the expected output spelling in your sample
    }


}
