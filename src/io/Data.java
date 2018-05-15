package io;

import java.util.HashMap;
import java.util.Map;

abstract class Data {
    private static Map<Integer, Person> users = new HashMap<Integer, Person>();


    private static Course[] courses = {
            new Course("ICS2O", "Computer Studies", new Section[]{
                new Section("ICS2O1/3-01",   new Person[] { new Person(334916053, "Ahlaam", "Ali", null, 'F'),
                        new Person(235184, "Owain", "Jones", null, 'M'),
                        new Person(343332318, "Jeremias", "Almazan", null, 'M'),
                        new Person(325346195, "Asmaa", "Bhagat", null, 'F'),
                        new Person(333334845, "Rashaud", "Chin-Butler", null, 'M'),
                        new Person(325307650, "Nadia", "Duble", null, 'F'),
                        new Person(333278802, "Ihtishamul", "Haque", null, 'M'),
                        new Person(325261931, "Samir", "Hossain", null, 'M'),
                        new Person(333927002, "Kara", "Huynh", null, 'F'),
                        new Person(325324952, "Deshanth", "Jegathasan", null, 'M'),
                        new Person(325347268, "Ma'az", "Kazi", null, 'M'),
                        new Person(345350813, "Ethan", "Lousenberg", null, 'M'),
                        new Person(333356871, "Ajahlon", "Miller", "Miller", 'M'),
                        new Person(333685493, "Samin", "Nabi", null, 'M'),
                        new Person(325267128, "Om", "Patel", null, 'M'),
                        new Person(333292605, "Ammaar", "Shaikh", null, 'M'),
                        new Person(344097498, "Ola", "Tijani", null, 'M'),
                        new Person(324657014, "Maryama", "Ulusow", null, 'F'),
                        new Person(334307667, "Jayden", "Vuong", null, 'M'),
                        new Person(333364966, "Nishanth", "Yohanathan", null, 'M')})
            })
    };

    public static Course[] getCourses() {
        return courses;
    }

    static Person getUser(int id) {
        return users.get(id);
    }

    static void initialize() {
        for (int i = 0; i < courses.length; i++) {
            for (int j = 0; j < courses[i].getSections().length; j++) {
                courses[i].getSections()[j].setCourse(courses[i]);

                for (int k = 0; k < courses[i].getSections()[j].getPeople().length; k++) {
                    courses[i].getSections()[j].getPeople()[k].addSection(courses[i].getSections()[j]);

                    if (users.containsKey(courses[i].getSections()[j].getPeople()[k].getId())) {
                        users.get(courses[i].getSections()[j].getPeople()[k].getId()).addSection(courses[i].getSections()[j]);
                    } else {
                        users.put(courses[i].getSections()[j].getPeople()[k].getId(), courses[i].getSections()[j].getPeople()[k]);
                    }
                }
            }
        }
    }


}
