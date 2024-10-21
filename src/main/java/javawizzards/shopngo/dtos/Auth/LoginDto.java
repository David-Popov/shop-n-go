package javawizzards.shopngo.dtos.Auth;

public class LoginDto {
    private String googleId;

    public LoginDto() {
    }

    public LoginDto(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}
