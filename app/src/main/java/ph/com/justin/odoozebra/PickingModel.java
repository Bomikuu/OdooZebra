package ph.com.justin.odoozebra;

public class PickingModel {
    private Integer id;
    private String name;

    public PickingModel() {
    }

    public PickingModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
