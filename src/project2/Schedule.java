package project2;

import util.List;
import util.Sort;

/**
 * Schedule is a list of Section objects.
 * @author Lucas Barrales
 */
public class Schedule extends List<Section> {


    public util.List<Section> sectionsEnrolled(Student student) {
        util.List<Section> enrolled = new util.List<>();
        for (Section s : this) {
            if (s.contains(student)) enrolled.add(s);
        }
        return enrolled;
    }


    public Section getSection(Course course, Time time) {
        for (int i = 0; i < size(); i++) {
            Section s = get(i);
            if (s.getCourse() == course && s.getTime() == time) return s;
        }
        return null;
    }

    public boolean hasInstructorConflict(Instructor instructor, Time time) {
        for (Section s : this) {
            if (s.getInstructor() == instructor && s.getTime() == time) return true;
        }
        return false;
    }

    public boolean hasClassroomConflict(Classroom classroom, Time time) {
        for (Section s : this) {
            if (s.getClassroom() == classroom && s.getTime() == time) return true;
        }
        return false;
    }

    public boolean isEnrolledInCourse(Student student, Course course) {
        for (Section s : this) {
            if (s.getCourse() == course && s.contains(student)) return true;
        }
        return false;
    }

    /** Total enrolled credits for a student based on current schedule rosters. */
    public int creditsEnrolled(Student student) {
        int total = 0;
        for (Section s : this) {
            if (s.contains(student)) total += s.getCourse().getCreditHours();
        }
        return total;
    }

    /** Close a section by course+time with enrolled check. */
    public void close(Course course, Time time) {
        Section s = getSection(course, time);
        if (s == null) {
            System.out.println("INVALID: " + course.name() + " " + formatTime(time) + " does not exist.");
            return;
        }

        int enrolled = s.getNumStudents(); // you already have this in Section
        String label = course.name() + " " + formatTime(time);

        if (enrolled > 0) {
            System.out.println(label + " cannot be removed [" + enrolled + " student(s) enrolled]");
            return;
        }

        remove(s);
        System.out.println(label + " removed.");
    }

    public String drop(Course course, Time time, Student student) {
        Section sec = getSection(course, time);
        if (sec == null) {
            return "INVALID: " + course.name() + " " + formatTime(time) + " does not exist.\n";
        }

        if (!sec.contains(student)) {
            return "[" + student.getProfile() + "] is not enrolled in this section\n";
        }

        sec.drop(student);
        return "[" + student.getProfile() + "] dropped from " + course.name() + " " + formatTime(time) + "\n";
    }

    public String enroll(Course course, Time time, Student student) {
        Section actual = getSection(course, time);
        if (actual == null) {
            return "INVALID: " + course.name() + " " + formatTime(time) + " does not exist.\n";
        }

        if (isEnrolledInCourse(student, course)) {
            return "[" + student.getProfile() + "] already enrolled in " + course.name() + "\n";
        }

        String reqMajor = course.getReqMajor();
        if (reqMajor != null) {
            String required = reqMajor.trim().split("\\s+")[0].toUpperCase();
            if (!student.getMajor().name().equalsIgnoreCase(required)) {
                return "Prereq: major only - [" + student.getProfile() + "] [" + student.getMajor().name() + "]\n";
            }
        }

        String prereqStanding = course.getStanding();
        if (prereqStanding != null && !meetsStanding(student, prereqStanding)) {
            return "Prereq: " + prereqStanding + " - [" + student.getProfile() + "] [" + student.getStanding() + "]\n";
        }

        for (Section s : this) {
            if (s.getTime() == time && s.contains(student)) {
                return "Time conflict: [" + student.getProfile()
                        + "] enrolled in another class at " + formatTime(time) + ".\n";
            }
        }

        int currentCredits = creditsEnrolled(student);
        int attemptedTotal = currentCredits + course.getCreditHours();
        if (attemptedTotal > 20) {
            return "Cannot enroll [" + student.getProfile()
                    + "]; now has " + currentCredits
                    + " will exceeds credit limit of 20.\n";
        }

        if (actual.isFull()) {
            return "Cannot enroll [" + student.getProfile() + "], "
                    + course.name() + " " + formatTime(time) + " is full.\n";
        }

        actual.enroll(student);
        return "[" + student.getProfile() + "] added to " + course.name() + " " + formatTime(time) + "\n";
    }

    public void printByCourse() {
        if (isEmpty()) {
            System.out.println("Schedule is empty!");
            return;
        }
        System.out.println("* List of sections ordered by course number, section time *");
        Schedule copy = new Schedule();
        for (Section s : this) copy.add(s);
        Sort.sortByCourseThenTime(copy);
        for (Section s : copy) s.print();
        System.out.println("* end of list *");
    }

    public void printByLocation() {
        if (isEmpty()) {
            System.out.println("Schedule is empty!");
            return;
        }
        System.out.println("* List of sections ordered by campus, building *");
        Schedule copy = new Schedule();
        for (Section s : this) copy.add(s);
        Sort.sortByCampusBuilding(copy);
        for (Section s : copy) s.print();
        System.out.println("* end of list **");
    }

    private String formatTime(Time t) {
        return t.getHour() + ":" + String.format("%02d", t.getMinute());
    }

    private boolean meetsStanding(Student student, String needed) {
        return standingRank(student.getStanding()) >= standingRank(needed);
    }

    private int standingRank(String standing) {
        if (standing == null) return -1;
        if (standing.equalsIgnoreCase("Freshman")) return 0;
        if (standing.equalsIgnoreCase("Sophomore")) return 1;
        if (standing.equalsIgnoreCase("Junior")) return 2;
        if (standing.equalsIgnoreCase("Senior")) return 3;
        return -1;
    }
}