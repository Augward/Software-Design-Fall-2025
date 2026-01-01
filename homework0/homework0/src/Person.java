/**
 * Represents a person with a name.
 * <p>
 *     A simple class that represents a person and stores their name.
 *     Contains setters, getters, and a toString method.
 * </p>
 */

public class Person {
    private String name;

    /**
     * Constructs a person with a given name.
     *
     * @param name the name of the person
     */
    public Person(String name) {
        this.name = name;
    }


    /**
     * Returns the person's name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the person's new name.
     *
     * @param newName the new name of the person.
     */
    public void setName(String newName) {
        this.name = newName;
    }


    /**
     * Returns a string of the person's name.
     *
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }
}
