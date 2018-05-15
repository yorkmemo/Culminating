package io;

public class Section {
    private String code;
    private Course course;
    private Person[] people;

    public Section(String code, Person[] people) {
        this.code = code;
        this.people = people;
    }

    public String getCode() {
        return code;
    }

    public Course getCourse() {
        return course;
    }

    public Person[] getPeople() {
        return people;
    }

    void setCourse(Course course) {
        this.course = course;
    }
}
