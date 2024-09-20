package app.pi_fisio.entity;

public enum PersonRole {
    USER("user"),
    ADMIN("admin");

    private String role;

    PersonRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
}
