import java.util.Arrays;

/**
 * Driver class to demonstrate the functionality of the classes in homework 0.
 * <p>
 *     Creates two teachers, seven students, two classrooms and prints the information.
 *     Prints roll call, lunch report, and ID out for tests.
 * </p>
 *
 */

public class Driver {
    public static void main(String[] args) {
        // Creates teacher for classroom 1
        Teacher t1 = new Teacher("Alex", "Math", "Bachelors", "Iowa State");

        // Creates 3 students for classroom 1
        Student s1 = new Student("Grace", "Cold");
        Student s2 = new Student("Max", "Cold");
        Student s3 = new Student("Hank", "Hot");

        // Creates classroom with above people
        Classroom c1 = new Classroom(t1, 3, Arrays.asList(s1, s2, s3));

        // Prints out details of classroom 1
        System.out.println("Classroom 1");
        System.out.println("Teacher ID: " + c1.getID());
        System.out.println(c1);
        System.out.println("Roll Call: " + c1.rollCall());
        System.out.println("Lunch Report: " + c1.generateLunchReport());
        System.out.println();


        // Creates teacher for classroom 2
        Teacher t2 = new Teacher("Thomas", "Science", "PhD", "University of Iowa");

        // Creates 3 students for classroom 2
        Student s4 = new Student("Ronald", "Hot");
        Student s5 = new Student("Al", "Hot");
        Student s6 = new Student("Denis", "Hot");
        Student s7 = new Student("Jack", "Cold");

        // Creates classroom with above people
        Classroom c2 = new Classroom(t2,11, Arrays.asList(s4, s5, s6, s7));

        // Prints out details of classroom 2
        System.out.println("Classroom 2");
        System.out.println("Teacher ID: " + c2.getID());
        System.out.println(c2);
        System.out.println("Roll Call: " + c2.rollCall());
        System.out.println("Lunch Report: " + c2.generateLunchReport());
        System.out.println();

        // Prints out the next possible teacher ID
        System.out.println("Next Teacher ID: " + t1.getNextID());
        System.out.println();


        // Extra testing to get rid of warnings and check functionality.
        System.out.println("Extra Testing:");

        // Setters for teacher class
        t1.setName("August");
        t1.setSubject("Algebra");
        t1.setDegree("Doctorate");
        t1.setUniversity("University of Illinois");
        System.out.println(t1.getName() + ", " + t1.getSubject() + ", " + t1.getDegree() + ", " + t1.getUniversity() + ", ID " + t1.getID());

        // Setter for student class
        s1.setLunchPreference("Hot");

        // Setters for classroom class
        c1.setTeacher(t2);
        c1.setGradeLevel(5);
        c1.setStudents(Arrays.asList(s1, s2, s3, s4, s5, s6, s7));
        System.out.println(c1.getTeacher().getName() + ", " + c1.getGradeLevel());
        for (Student s : c1.getStudents()) {
            System.out.println(s.getName());
        }
    }
}