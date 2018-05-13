package ua.khnu.shtefanyankovska.entity;

public class User extends Entity {
    private static final long serialVersionUID = 1L;
    private String login;
    private String name;
    private String sname;
    private String email;
    private String pass;
    private boolean canWork;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isValid(String pass) {
        return pass.equals(this.pass);
    }

    @Override
    public String toString() {
        return login;
    }

    public boolean isCanWork() {
        return canWork;
    }

    public void setCanWork(boolean canWork) {
        this.canWork = canWork;
    }
}
