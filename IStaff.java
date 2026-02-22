public interface IStaff {
    String getId();
    String getUsername();
    String getPassword();
    String getRole();
    boolean can(String action);
}
