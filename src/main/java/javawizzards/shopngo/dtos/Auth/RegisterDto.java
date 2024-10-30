package javawizzards.shopngo.dtos.Auth;

public class RegisterDto {
    private String email;
    private String username;
    private String googleId;
    private String pictureUrl;
    private String phoneNumber;

    public RegisterDto(String email, String username, String googleId, String pictureUrl, String phoneNumber) {
        this.email = email;
        this.username = username;
        this.googleId = googleId;
        this.pictureUrl = pictureUrl;
        this.phoneNumber = phoneNumber;
    }

    public RegisterDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
