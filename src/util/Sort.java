package util;

import project2.Student;
import project2.Section;
import project2.Profile;


public class Sort {

    private Sort() {}

    /** Insertion sort students by Profile (last, first, dob) */
    public static void sortByProfile(List<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            Student key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getProfile().compareTo(key.getProfile()) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    /** Insertion sort students by Major then Profile */
    public static void sortByMajor(List<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            Student key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compareMajorThenProfile(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private static int compareMajorThenProfile(Student a, Student b) {
        int c = a.getMajor().name().compareTo(b.getMajor().name());
        if (c != 0) return c;
        return a.getProfile().compareTo(b.getProfile());
    }

    /** Insertion sort sections by course number then time */
    public static void sortByCourseThenTime(List<Section> list) {
        for (int i = 1; i < list.size(); i++) {
            Section key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compareCourseThenTime(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private static int compareCourseThenTime(Section a, Section b) {
        int c = a.getCourse().name().compareTo(b.getCourse().name());
        if (c != 0) return c;
        return Integer.compare(a.getTime().ordinal(), b.getTime().ordinal());
    }

    /** Insertion sort sections by campus then building */
    public static void sortByCampusBuilding(List<Section> list) {
        for (int i = 1; i < list.size(); i++) {
            Section key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compareCampusBuilding(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private static int compareCampusBuilding(Section a, Section b) {
        int c = a.getClassroom().getCampus().compareToIgnoreCase(b.getClassroom().getCampus());
        if (c != 0) return c;
        return a.getClassroom().getBuilding().compareToIgnoreCase(b.getClassroom().getBuilding());
    }
}
