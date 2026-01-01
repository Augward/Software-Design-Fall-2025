/**
 * Represents a student, which is a person with a lunch preference.
 * <p>
 *     An inheritance class that represents a student and stores their name
 *     and lunch preference.
 *     Contains setters, getters, and a toString method.
 * </p>
 */

public class Student extends Person {
    private String lunchPreference;

    /**
     * Constructs a student with a name and lunch preference.
     *
     * @param name the name of the student, based on person class.
     * @param lunchPreference the lunch preference of the student, whether hot or cold
     */
    public Student(String name, String lunchPreference) {
        super(name);

        this.lunchPreference = lunchPreference;
    }


    /**
     * Returns the student's lunch preference.
     *
     * @return lunchPreference
     */
    public String getLunchPreference() {
        return lunchPreference;
    }

    /**
     * Sets the student's new lunch preference.
     *
     * @param newLunchPreference the new lunch preference of the student.
     */
    public void setLunchPreference(String newLunchPreference) {
        this.lunchPreference = newLunchPreference;
    }


    /**
     * Returns a string with the student's details.
     *
     * @return name, lunchPreference
     */
    @Override
    public String toString() {
        return String.format("I am %s, who prefers %s lunch.", getName(), lunchPreference);
    }
}
