package au.com.geekseat.security;

import au.com.geekseat.helper.Utility;

import java.util.List;

public class Principal {

    private Long id;
    private String name;
    private String email;
    private String role;
    private List<String> states;
    private boolean administrator;
    private String token;

    public static final Principal System = new Principal(null, null, null, null, null, false);

    public Principal(Long id, String name, String email, String role, List<String> states, boolean administrator) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.states = states;
        this.administrator = administrator;
    }

    public Principal(Long id, String firstName, String lastName, String email, String role, List<String> states, boolean administrator) {
        this.id = id;
        this.name = Utility.strip(Utility.stringify(firstName) + " " + Utility.stringify(lastName));
        this.email = email;
        this.role = role;
        this.states = states;
        this.administrator = administrator;
    }

    public Principal essence() {
        return new Principal(this.id, this.name, this.email, this.role, this.states, this.administrator);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
