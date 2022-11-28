package pl.btsoftware.users;

@FunctionalInterface
public interface VerifyUser {
    boolean isValid(UserId userId);
}
