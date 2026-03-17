package user;
public interface IStaff {
    String getId();
    String getUsername();
    String getPassword();
    String getSignature();
    boolean can(String action);
}
