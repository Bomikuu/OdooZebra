package ph.com.justin.odoozebra;

public class PickingModel {
    private Integer id;
    private String name;
    private String origin;
    private String min_date;
    private String max_date;
    private String date;
    private String date_done;
    private String location_src;
    private String location_dest;
    private String picking_type;
    private String partner;

    public PickingModel() {
    }

    public PickingModel(Integer id, String name, String origin, String min_date, String max_date, String date, String date_done, String location_src, String location_dest, String picking_type, String partner) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.min_date = min_date;
        this.max_date = max_date;
        this.date = date;
        this.date_done = date_done;
        this.location_src = location_src;
        this.location_dest = location_dest;
        this.picking_type = picking_type;
        this.partner = partner;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMin_date() {
        return min_date;
    }

    public void setMin_date(String min_date) {
        this.min_date = min_date;
    }

    public String getMax_date() {
        return max_date;
    }

    public void setMax_date(String max_date) {
        this.max_date = max_date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_done() {
        return date_done;
    }

    public void setDate_done(String date_done) {
        this.date_done = date_done;
    }

    public String getLocation_src() {
        return location_src;
    }

    public void setLocation_src(String location_src) {
        this.location_src = location_src;
    }

    public String getLocation_dest() {
        return location_dest;
    }

    public void setLocation_dest(String location_dest) {
        this.location_dest = location_dest;
    }

    public String getPicking_type() {
        return picking_type;
    }

    public void setPicking_type(String picking_type) {
        this.picking_type = picking_type;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
