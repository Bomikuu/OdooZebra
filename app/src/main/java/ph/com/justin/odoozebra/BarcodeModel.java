package ph.com.justin.odoozebra;

/**
 * Created by irvin on 5/4/18.
 */
public class BarcodeModel {

    private String value;
    private String product_id;

    public BarcodeModel() {
    }

    public BarcodeModel(String value, String product_id) {
        this.value = value;
        this.product_id = product_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
