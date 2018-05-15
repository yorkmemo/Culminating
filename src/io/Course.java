package io;

public class Course {
    private String code;
    private String name;
    private Section[] sections;

    public Course(String code, String name, Section[] sections) {
        this.code = code;
        this.name = name;
        this.sections = sections;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Section[] getSections() {
        return sections;
    }
}
