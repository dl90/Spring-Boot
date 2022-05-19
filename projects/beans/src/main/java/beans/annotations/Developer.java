package beans.annotations;

import org.springframework.stereotype.Component;

@Component
public class Developer {

    private String role;

    public Developer(){
    }

    public Developer(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Developer{" + "role='" + role + '\'' + '}';
    }
}
