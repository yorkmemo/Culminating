package io;

import java.util.Arrays;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String prefName;
    private char gender;
    private Section[] sections = {};

    public Person(int id, String firstName, String lastName, String prefName, char gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.prefName = prefName;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return isTeacher() ? (gender == 'M' || gender == 'm' ? "Mr. " : (gender == 'F' || gender == 'f' ? "Ms. " : "Mx. ")) + lastName : firstName + " " + lastName;
    }

    public String getName() {
        return isTeacher() ? (gender == 'M' || gender == 'm' ? "Mr. " : (gender == 'F' || gender == 'f' ? "Ms. " : "Mx. ")) + lastName : (prefName == null ? firstName : prefName);
    }


    public boolean isMale() {
        return gender == 'M' || gender == 'm';
    }

    public boolean isFemale() {
        return gender == 'F' || gender == 'f';
    }

    void addSection(Section section) {
        sections = Arrays.copyOf(sections, sections.length + 1);
        sections[sections.length - 1] = section;
    }

    public Section[] getSections() {
        return sections;
    }

    public boolean isTeacher() {
        return id <= 999999;
    }
}
