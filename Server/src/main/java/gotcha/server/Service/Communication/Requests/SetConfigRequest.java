package gotcha.server.Service.Communication.Requests;

/**
 * LoginRequest is a data transfer object (DTO) that represents the
 * login request payload containing the email and password for
 * authentication.
 */

public class SetConfigRequest {
    // TODO: 12/04/2023  
    private String email;
    private String password;

    // Default constructor (required for deserialization)
    public SetConfigRequest() {
    }

    // Custom constructor
    public SetConfigRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

