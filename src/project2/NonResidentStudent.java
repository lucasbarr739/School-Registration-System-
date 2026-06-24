package project2;

public class NonResidentStudent extends NonResident {

    public NonResidentStudent(Profile profile, Major major, int credits) {
        super(profile, major, credits);
    }

    @Override
    public String getStudentTypeTag() {
        return "Noresident"; // match expected output spelling
    }
}