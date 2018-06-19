package ph.com.justin.odoozebra;

public class ProductLotsModel {
    private Integer id;
    private Boolean name;

    public ProductLotsModel() {
    }

    public ProductLotsModel(Integer id, Boolean name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getName() {
        return name;
    }

    public void setName(Boolean name) {
        this.name = name;
    }
}
