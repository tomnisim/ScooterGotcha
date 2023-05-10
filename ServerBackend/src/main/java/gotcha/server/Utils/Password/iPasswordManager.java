package gotcha.server.Utils.Password;

public interface iPasswordManager {
    String hash(String password);
    boolean authenticate(String password, String token);
}