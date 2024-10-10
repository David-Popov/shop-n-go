package javawizzards.shopngo.dtos.Category.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
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
