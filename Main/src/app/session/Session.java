package app.session;

public class Session {
    private static String userId;
    private static String role;
    private static String username;
    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setRole(String r) {
        role = r;
    }

    public static String getRole() {
        return role;
    }

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void clear() {
        userId = null;
        role = null;
        username = null;
    }
}
