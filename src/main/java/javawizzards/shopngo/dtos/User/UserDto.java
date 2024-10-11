package javawizzards.shopngo.dtos.User;

public class UserDto {
    private String googleId;
    private String email;
    private String username;
    private String pictureUrl;
    private String phoneNumber;

    public UserDto() {
    }

    public UserDto(String googleId, String email, String username, String pictureUrl, String phoneNumber) {
        this.username = username;
        this.googleId = googleId;
        this.email = email;
        this.username = username;
        this.pictureUrl = pictureUrl;
        this.phoneNumber = phoneNumber;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
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
