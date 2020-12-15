package il.co.expertize.emailauthfirebase.Entities;

import android.widget.ImageView;

public class User {

    private String email;
    private String fullName;
    private String phone;
    private String password;
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public User(String email, String password,String fullName,  String phone,ImageView imageView) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.password=password;
        this.imageView=imageView;
    }
}