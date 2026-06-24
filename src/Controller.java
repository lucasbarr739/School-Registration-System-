import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import project2.Classroom;
import project2.Course;
import project2.Instructor;
import project2.International;
import project2.Major;
import project2.Profile;
import project2.Resident;
import project2.Schedule;
import project2.Section;
import project2.Student;
import project2.StudentList;
import project2.Time;
import project2.TriState;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final StudentList studentList = new StudentList();
    private final Schedule schedule = new Schedule();
    private final ToggleGroup studentTypeGroup = new ToggleGroup();

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField dobField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField creditsField;

    @FXML
    private RadioButton residentBtn;

    @FXML
    private RadioButton triStateBtn;

    @FXML
    private RadioButton internationalBtn;

    @FXML
    private TextField stateField;

    @FXML
    private CheckBox studyAbroadCheck;

    @FXML
    private TextField enrollFirstNameField;

    @FXML
    private TextField enrollLastNameField;

    @FXML
    private TextField enrollDobField;

    @FXML
    private TextField courseField;

    @FXML
    private TextField timeField;

    @FXML
    private TextArea outputArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        residentBtn.setToggleGroup(studentTypeGroup);
        triStateBtn.setToggleGroup(studentTypeGroup);
        internationalBtn.setToggleGroup(studentTypeGroup);

        initializeSchedule();
    }

    @FXML
    private void handleAddStudent() {
        try {
            String first = firstNameField.getText().trim();
            String last = lastNameField.getText().trim();
            String dobStr = dobField.getText().trim();
            String majorStr = majorField.getText().trim().toUpperCase();
            String creditsStr = creditsField.getText().trim();

            if (first.isEmpty() || last.isEmpty() || dobStr.isEmpty()
                    || majorStr.isEmpty() || creditsStr.isEmpty()) {
                outputArea.appendText("Error: All student fields must be filled.\n");
                return;
            }

            int credits = Integer.parseInt(creditsStr);
            if (credits < 0) {
                outputArea.appendText("Error: Credits cannot be negative.\n");
                return;
            }

            util.Date dob = parseDate(dobStr);
            if (dob == null || !dob.isValid()) {
                outputArea.appendText("Error: Invalid DOB. Use MM/DD/YYYY.\n");
                return;
            }

            Major major;
            try {
                major = Major.valueOf(majorStr);
            } catch (IllegalArgumentException e) {
                outputArea.appendText("Error: Invalid major. Use CS, ECE, MATH, ITI, or BAIT.\n");
                return;
            }

            Profile profile = new Profile(first, last, dob);

            if (studentList.findByProfile(profile) != null) {
                outputArea.appendText("Error: Student already exists.\n");
                return;
            }

            Student student;

            if (residentBtn.isSelected()) {
                student = new Resident(profile, major, credits);
            } else if (triStateBtn.isSelected()) {
                String state = stateField.getText().trim().toUpperCase();
                if (!state.equals("NY") && !state.equals("CT")) {
                    outputArea.appendText("Error: TriState state must be NY or CT.\n");
                    return;
                }
                student = new TriState(profile, major, credits, state);
            } else if (internationalBtn.isSelected()) {
                boolean studyAbroad = studyAbroadCheck.isSelected();
                student = new International(profile, major, credits, studyAbroad);
            } else {
                outputArea.appendText("Error: Select a student type.\n");
                return;
            }

            studentList.add(student);

            outputArea.appendText("Student added successfully:\n");
            outputArea.appendText(student.printStudentLine() + "\n\n");

            clearStudentFields();

        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Credits must be a valid number.\n");
        } catch (Exception e) {
            outputArea.appendText("Unexpected error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleRemoveStudent() {
        Student student = getStudentFromStudentFields();

        if (student == null) {
            outputArea.appendText("Error: Student not found.\n");
            return;
        }

        studentList.remove(student);
        outputArea.appendText("Student removed successfully:\n");
        outputArea.appendText(student.printStudentLine() + "\n\n");
    }

    @FXML
    private void handlePrintStudents() {
        if (studentList.isEmpty()) {
            outputArea.appendText("Student list is empty!\n");
            return;
        }

        outputArea.appendText("* Student List *\n");
        for (int i = 0; i < studentList.size(); i++) {
            outputArea.appendText(studentList.get(i).printStudentLine() + "\n");
        }
        outputArea.appendText("* End of List *\n\n");
    }

    @FXML
    private void handleEnroll() {
        try {
            Student student = getStudentFromEnrollmentFields();
            if (student == null) {
                outputArea.appendText("Error: Student not found.\n");
                return;
            }

            String courseStr = courseField.getText().trim().toUpperCase();
            String timeStr = timeField.getText().trim();

            if (courseStr.isEmpty() || timeStr.isEmpty()) {
                outputArea.appendText("Error: Course and time are required.\n");
                return;
            }

            Course course = Course.valueOf(courseStr);
            Time time = parseTime(timeStr);

            if (time == null) {
                outputArea.appendText("Error: Invalid time value.\n");
                return;
            }

            String result = schedule.enroll(course, time, student);
            outputArea.appendText(result + "\n");

        } catch (IllegalArgumentException e) {
            outputArea.appendText("Error: Invalid course.\n");
        } catch (Exception e) {
            outputArea.appendText("Error: Invalid enrollment input.\n");
        }
    }

    @FXML
    private void handleDrop() {
        try {
            Student student = getStudentFromEnrollmentFields();
            if (student == null) {
                outputArea.appendText("Error: Student not found.\n");
                return;
            }

            String courseStr = courseField.getText().trim().toUpperCase();
            String timeStr = timeField.getText().trim();

            if (courseStr.isEmpty() || timeStr.isEmpty()) {
                outputArea.appendText("Error: Course and time are required.\n");
                return;
            }

            Course course = Course.valueOf(courseStr);
            Time time = parseTime(timeStr);

            if (time == null) {
                outputArea.appendText("Error: Invalid time value.\n");
                return;
            }

            String result = schedule.drop(course, time, student);
            outputArea.appendText(result + "\n");

        } catch (IllegalArgumentException e) {
            outputArea.appendText("Error: Invalid course.\n");
        } catch (Exception e) {
            outputArea.appendText("Error: Invalid drop input.\n");
        }
    }

    @FXML
    private void handlePrintScheduleByCourse() {
        if (schedule.isEmpty()) {
            outputArea.appendText("Schedule is empty!\n");
            return;
        }

        outputArea.appendText("* Schedule ordered by course *\n");

        for (Course c : Course.values()) {
            for (int i = 0; i < schedule.size(); i++) {
                Section s = schedule.get(i);
                if (s.getCourse() == c) {
                    outputArea.appendText(s.toString() + "\n");
                }
            }
        }

        outputArea.appendText("* End of Schedule *\n\n");
    }

    @FXML
    private void handlePrintScheduleByLocation() {
        if (schedule.isEmpty()) {
            outputArea.appendText("Schedule is empty!\n");
            return;
        }

        outputArea.appendText("* Schedule ordered by location *\n");

        for (Classroom room : Classroom.values()) {
            for (int i = 0; i < schedule.size(); i++) {
                Section s = schedule.get(i);
                if (s.getClassroom() == room) {
                    outputArea.appendText(s.toString() + "\n");
                }
            }
        }

        outputArea.appendText("* End of Schedule *\n\n");
    }

    @FXML
    private void handlePrintTuitionReport() {
        if (studentList.isEmpty()) {
            outputArea.appendText("Student list is empty!\n");
            return;
        }

        outputArea.appendText("* Tuition Report *\n");

        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            int credits = schedule.creditsEnrolled(s);
            double tuition = s.tuition(credits);

            outputArea.appendText("--------------------------------\n");
            outputArea.appendText(s.printStudentLine() + "\n");
            outputArea.appendText("Credits: " + credits + "\n");
            outputArea.appendText("Tuition: $" + String.format("%.2f", tuition) + "\n");
        }

        outputArea.appendText("--------------------------------\n");
        outputArea.appendText("* End of Report *\n\n");
    }

    @FXML
    private void handleClearOutput() {
        outputArea.clear();
    }

    private Student getStudentFromStudentFields() {
        String first = firstNameField.getText().trim();
        String last = lastNameField.getText().trim();
        String dobStr = dobField.getText().trim();

        if (first.isEmpty() || last.isEmpty() || dobStr.isEmpty()) {
            return null;
        }

        util.Date dob = parseDate(dobStr);
        if (dob == null || !dob.isValid()) {
            return null;
        }

        Profile profile = new Profile(first, last, dob);
        return studentList.findByProfile(profile);
    }

    private Student getStudentFromEnrollmentFields() {
        String first = enrollFirstNameField.getText().trim();
        String last = enrollLastNameField.getText().trim();
        String dobStr = enrollDobField.getText().trim();

        if (first.isEmpty() || last.isEmpty() || dobStr.isEmpty()) {
            return null;
        }

        util.Date dob = parseDate(dobStr);
        if (dob == null || !dob.isValid()) {
            return null;
        }

        Profile profile = new Profile(first, last, dob);
        return studentList.findByProfile(profile);
    }

    private util.Date parseDate(String dobStr) {
        try {
            String[] parts = dobStr.split("/");
            if (parts.length != 3) {
                return null;
            }

            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            return new util.Date(month, day, year);
        } catch (Exception e) {
            return null;
        }
    }

    private Time parseTime(String timeStr) {
        try {
            return Time.valueOf(timeStr.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private void clearStudentFields() {
        firstNameField.clear();
        lastNameField.clear();
        dobField.clear();
        majorField.clear();
        creditsField.clear();
        stateField.clear();
        studyAbroadCheck.setSelected(false);
        studentTypeGroup.selectToggle(null);
    }

    private void initializeSchedule() {
        schedule.add(new Section(Course.CS100, Time.periodOne, Instructor.PATEL, Classroom.HIL114));
        schedule.add(new Section(Course.CS200, Time.periodTwo, Instructor.LIM, Classroom.ARC103));
        schedule.add(new Section(Course.CS300, Time.periodThree, Instructor.ZIMNES, Classroom.BEAUD));
        schedule.add(new Section(Course.CS400, Time.periodFour, Instructor.HARPER, Classroom.TIL232));

        schedule.add(new Section(Course.ECE300, Time.periodFive, Instructor.KAUR, Classroom.AB2225));
        schedule.add(new Section(Course.ECE400, Time.periodSix, Instructor.TAYLOR, Classroom.MU302));

        schedule.add(new Section(Course.PHY100, Time.periodOne, Instructor.RAMESH, Classroom.HIL114));
        schedule.add(new Section(Course.PHY200, Time.periodTwo, Instructor.CERAVOLO, Classroom.ARC103));

        schedule.add(new Section(Course.CCD, Time.periodThree, Instructor.BROWN, Classroom.BEAUD));
    }
}