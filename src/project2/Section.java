package project2;

/**
 * Represents one section in the schedule.
 * @author Lucas Barrales
 */
public class Section {
    private final Course course;
    private final Time time;
    private final Instructor instructor;
    private final Classroom classroom;

    private final StudentList roster; // ✅ Project2: use StudentList, not arrays

    public Section(Course course, Time time, Instructor instructor, Classroom classroom) {
        this.course = course;
        this.time = time;
        this.instructor = instructor;
        this.classroom = classroom;
        this.roster = new StudentList();
    }

    public Course getCourse() { return course; }
    public Time getTime() { return time; }
    public Instructor getInstructor() { return instructor; }
    public Classroom getClassroom() { return classroom; }

    public int getNumStudents() { // ✅ needed by Schedule remove logic
        return roster.size();
    }

    public boolean contains(Student s) {
        return roster.contains(s);
    }

    public boolean isFull() {
        return roster.size() >= 4;
    }

    public void enroll(Student s) {
        roster.add(s);
    }

    public void drop(Student s) {
        roster.remove(s);
    }

    private String formatTime(Time t) {
        return t.getHour() + ":" + String.format("%02d", t.getMinute());
    }

    @Override
    public String toString() {
        return "[" + course.name() + " " + formatTime(time) + "] "
                + "[" + instructor.name().toUpperCase() + "] "
                + classroom.toString();
    }

    public void print() {
        System.out.println(this);
        if (roster.isEmpty()) {
            System.out.println("\t**No students enrolled**");
            return;
        }
        System.out.println("\t**Roster**");
        for (Student s : roster) {
            System.out.println("\t[" + s.getProfile() + "]");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Section)) return false;
        Section other = (Section) o;
        return this.course == other.course && this.time == other.time;
    }

    public String tuitionLine() {
        String timeStr = String.format("%02d:%02d", time.getHour(), time.getMinute());
        return course.name() + "[" + timeStr + "] [credit: " + course.getCreditHours() + "]";
    }
}