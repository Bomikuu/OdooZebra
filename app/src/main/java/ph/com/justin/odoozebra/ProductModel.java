package ph.com.justin.odoozebra;

public class ProductModel {
    private Integer id;
    private String name;
    private Integer picking;
    private Integer product_qty;
    private Integer qty_done;
    private Integer qty_ordered;

    public ProductModel() {
    }

    public ProductModel(Integer id, String name, Integer picking, Integer product_qty, Integer qty_done, Integer qty_ordered) {
        this.id = id;
        this.name = name;
        this.picking = picking;
        this.product_qty = product_qty;
        this.qty_done = qty_done;
        this.qty_ordered = qty_ordered;
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

    public Integer getPicking() {
        return picking;
    }

    public void setPicking(Integer picking) {
        this.picking = picking;
    }

    public Integer getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(Integer product_qty) {
        this.product_qty = product_qty;
    }

    public Integer getQty_done() {
        return qty_done;
    }

    public void setQty_done(Integer qty_done) {
        this.qty_done = qty_done;
    }

    public Integer getQty_ordered() {
        return qty_ordered;
    }

    public void setQty_ordered(Integer qty_ordered) {
        this.qty_ordered = qty_ordered;
    }
}
