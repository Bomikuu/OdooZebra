package ph.com.justin.odoozebra;

public class ProductLotsModel {
    private String name;
    private Integer operation;

    public ProductLotsModel() {
    }

    public ProductLotsModel(String name, Integer operation) {
        this.name = name;
        this.operation = operation;
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
