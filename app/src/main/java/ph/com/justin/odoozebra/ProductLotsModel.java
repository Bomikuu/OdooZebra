package ph.com.justin.odoozebra;

public class ProductLotsModel {
    private String name;
    private Integer operation;
    private Integer
            picking;

    public ProductLotsModel() {
    }

    public ProductLotsModel(String name, Integer operation, Integer picking) {
        this.name = name;
        this.operation = operation;
        this.picking = picking;
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

    public Integer getPicking() {
        return picking;
    }

    public void setPicking(Integer picking) {
        this.picking = picking;
    }
}
