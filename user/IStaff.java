package user;
public interface IStaff {
    String getId();
    String getUsername();
    String getPassword();
    boolean can(String action);
    String getSignature();
}
