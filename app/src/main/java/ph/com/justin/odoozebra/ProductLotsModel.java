package ph.com.justin.odoozebra;

public class ProductLotsModel {
    private Integer id;
    private String name;
    private Integer operation;

    public ProductLotsModel() {
    }

    public ProductLotsModel(Integer id, String name, Integer operation) {
        this.id = id;
        this.name = name;
        this.operation = operation;
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

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
}
