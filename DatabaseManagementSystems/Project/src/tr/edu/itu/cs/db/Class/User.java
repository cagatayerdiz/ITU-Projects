package tr.edu.itu.cs.db.Class;

import java.sql.Date;


public class User {

    private Integer Id;
    private String userName;
    private String password;
    private String email;
    private String name;
    private String surName;
    private Date lastLogin;
    private Boolean isActive;
    private String confirmationCode;
    private Boolean isAdmin;

    public User(Integer id, String userName, String password, String email,
            String name, String surName, Date lastLogin, Boolean isActive,
            String confirmationCode, Boolean isAdmin) {
        super();
        Id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surName = surName;
        this.lastLogin = lastLogin;
        this.isActive = isActive;
        this.confirmationCode = confirmationCode;
        this.isAdmin = isAdmin;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public Boolean getIsActive() {

        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public User() {

    }

}
