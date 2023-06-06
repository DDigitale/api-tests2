package api.dto;

public class CreatedUser {
    private String name;
    private String job;

    public CreatedUser(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
