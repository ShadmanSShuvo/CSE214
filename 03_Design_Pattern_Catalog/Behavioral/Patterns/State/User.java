/**
 * Domain Actor: User
 * Represents a system user participating in document workflows.
 */
public class User {
    public enum Role {
        AUTHOR,
        EDITOR,
        ADMIN
    }

    private final String name;
    private final Role role;

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}
