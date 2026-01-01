/**
 * Represents a teacher, which is a person with a subject, degree, university, and ID.
 * <p>
 *     An inheritance class that represents a teacher and stores their name, subject,
 *     degree, university, and ID.
 *     Also has a static variable the represents the next viable ID for a new teacher.
 *     Contains setters, getters, and a toString method.
 * </p>
 */

public class Teacher extends Person {
    private String subject;
    private String degree;
    private String university;

    private final int ID;
    private static int nextID = 1;

    /**
     * Constructs a teacher with a name, subject, degree, university, and ID.
     *
     * @param name the name of the teacher, based on person class.
     * @param subject the subject the teacher teaches.
     * @param degree the level of degree the teacher holds.
     * @param university the place where they got their degree.
     */
    public Teacher(String name, String subject, String degree, String university) {
        super(name);

        this.subject = subject;
        this.degree = degree;
        this.university = university;

        ID = nextID;
        nextID++;
    }


    /**
     * Returns the teacher's subject.
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the teacher's new subject.
     *
     * @param newSubject the new subject of the teacher.
     */
    public void setSubject(String newSubject) {
        this.subject = newSubject;
    }

    /**
     * Returns the teacher's degree.
     *
     * @return degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the teacher's new degree.
     *
     * @param newDegree the new degree of the teacher.
     */
    public void setDegree(String newDegree) {
        this.degree = newDegree;
    }

    /**
     * Returns the teacher's university.
     *
     * @return university
     */
    public String getUniversity() {
        return university;
    }

    /**
     * Sets the teacher's new university.
     *
     * @param newUniversity the new university of the teacher.
     */
    public void setUniversity(String newUniversity) {
        this.university = newUniversity;
    }

    /**
     * Returns the teacher's ID.
     *
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the next ID that can be used for a new teacher.
     *
     * @return nextID
     */
    public int getNextID() {
        return nextID;
    }


    /**
     * Returns a string with the teacher's details.
     *
     * @return name, subject, degree, university, ID
     */
    @Override
    public String toString() {
        return String.format("I am %s, a teacher of %s. I hold a %s degree from %s, and my ID is %d", getName(), subject, degree, university, ID);
    }
}
