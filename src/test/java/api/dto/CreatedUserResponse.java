package api.dto;

public class CreatedUserResponse extends CreatedUser {
    private String updatedAt;
    private String createdAt;
    private Integer id;

    public CreatedUserResponse(String name, String job, String updatedAt, Integer id, String createdAt) {
        super(name, job);
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}


