package user;
public interface IStaff {
    String getId();
    String getUsername();
    String getPassword();
    boolean can(Permission permission);
    String getSignature();
}
