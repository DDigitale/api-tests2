package api.dto;

public class LoginResponse {
    private Integer id;
    private String token;
    private String error;

    public LoginResponse(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
