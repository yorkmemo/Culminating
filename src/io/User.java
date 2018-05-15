package io;

public abstract class User {
    private static final String accountName = System.getProperty("user.name").equals("oj") ? "235184" : System.getProperty("user.name");
    private static Person user;

    public static String account() {
        return accountName;
    }

    static void initialize() {
        try {
            int id = Integer.parseInt(accountName);

            user = Data.getUser(id);
        } catch (NumberFormatException e) {
            Output.error("Invalid User: " + accountName);
        }

    }

    public static String name() {
        return user == null ? null : user.getName();
    }

    public static String lastName() {
        return user == null ? null : user.getLastName();
    }

    public static String fullName() {
        return user == null ? null : user.getFullName();
    }

    public static boolean isTeacher() {
        return user == null || user.isTeacher();
    }

    public static Section[] getSections() {
        return user == null ? null : user.getSections();
    }

}
