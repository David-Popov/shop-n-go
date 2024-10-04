package javawizzards.shopngo.dtos.Category.Request;

public class CategoryDto {
    private long id;
    private String name;

    public CategoryDto(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public CategoryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
