import java.util.ArrayList;
import java.util.List;

/**
 * Represents a classroom, consists of students and a teacher.
 * <p>
 *     A composition class that represents a classroom and stores
 *     students, a teacher, and a grade level.
 *     Contains setters, getters, and a toString method.
 *     Also has roll call, generate lunch report, and a get ID method.
 *     Relies on importing Array List and List.
 * <p/>
 *
 */

public class Classroom {
    private Teacher teacher;
    private int gradeLevel;
    private List<Student> students;

    /**
     * Constructs a classroom with a teacher, students, and a grade level.
     *
     * @param teacher the teacher who instructs the class.
     * @param gradeLevel the grade level of the current class
     * @param students a list of students in the class.
     */
    public Classroom(Teacher teacher, Integer gradeLevel, List<Student> students) {
        this.teacher = teacher;
        this.gradeLevel = gradeLevel;
        this.students = new ArrayList<>(students);
    }


    /**
     * Returns the classroom's teacher.
     *
     * @return teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * Sets the classroom's new teacher.
     *
     * @param teacher the new teacher of the classroom.
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * Returns the classroom's grade level.
     *
     * @return gradeLevel
     */
    public int getGradeLevel() {
        return gradeLevel;
    }

    /**
     * Sets the classroom's new grade level.
     *
     * @param gradeLevel the new grade level of the classroom.
     */
    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * Returns the classroom's list of students.
     *
     * @return students
     */
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Sets the classroom's new students.
     *
     * @param students the new list of students of the classroom.
     */
    public void setStudents(List<Student> students) {
        this.students = new ArrayList<>(students);
    }


    /**
     * Returns a string with each student's name and lunch preference.
     *
     * @return rollCallList
     */
    public String rollCall() {
        StringBuilder rollCallList = new StringBuilder();
        for (Student s : students) {
            rollCallList.append(String.format("%s - %s, ", s.getName(), s.getLunchPreference()));
        }
        return rollCallList.toString();
    }

    /**
     * Returns a report of the number of hot and cold lunches are in a class.
     *
     * @return hot and cold lunches
     */
    public String generateLunchReport() {
        int hot = 0;
        int cold = 0;

        for (Student s : students) {
            String preference = s.getLunchPreference().toLowerCase();
            if (preference.equals("hot")) {
                hot++;
            } else if (preference.equals("cold")) {
                cold++;
            }
        }
        return String.format("Hot = %d | Cold = %d,", hot, cold);
    }

    /**
     * Returns a strings of teacher initials and grade level.
     *
     * @return teacher initial and grade level
     */
    public String getID() {
        return String.format("%c%d", teacher.getName().charAt(0), gradeLevel);
    }

    /**
     * Returns a string with the classroom's details.
     *
     * @return gradeLevel, name, size of class
     */
    @Override
    public String toString() {
        return String.format("This classroom has grade %d students. It is taught by %s. There are %d students.", gradeLevel, teacher.getName(), students.size());
    }
}
