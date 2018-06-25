package ph.com.justin.odoozebra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database version
    private static final int DATABASE_VERSION = 10;

    ///database name
    private static final String DATABASE_NAME = "Pickings";

    ///table name
    private static final String tbl_pickings = "tbl_pickings";
    ///columns...
    private static final String picking_id = "picking_id";
    private static final String picking_name = "picking_name";
    private static final String picking_origin = "picking_origin";
    private static final String picking_min_date = "picking_min_date";
    private static final String picking_max_date = "picking_max_date";
    private static final String picking_date = "picking_date";
    private static final String picking_date_done = "picking_date_done";
    private static final String picking_location_src = "picking_location_src";
    private static final String picking_location_dest = "picking_location_dest";
    private static final String picking_type = "picking_type";
    private static final String picking_partner = "picking_partner";

    ///table name
    private static final String tbl_products = "tbl_products";
    ///columns...
    private static final String product_id = "product_id";
    private static final String product_name = "product_name";
    private static final String product_picking_id = "product_picking_id";
    private static final String product_qty = "product_qty";
    private static final String product_qty_done = "product_qty_done";
    private static final String product_qty_ordered = "product_qty_ordered";

    ///table name
    private static final String tbl_product_lots = "tbl_product_lots";
    ///columns...
    private static final String product_lots_name = "product_lots_name";
    private static final String product_lots_operation_id = "product_lots_operation_id";
    private static final String product_lots_picking_id = "product_lots_picking_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PICKINGS_TABLE = "CREATE TABLE " + tbl_pickings + "("
                + picking_id + " integer primary key  , "
                + picking_name + " TEXT,"
                + picking_origin + " TEXT,"
                + picking_min_date + " TEXT,"
                + picking_max_date + " TEXT,"
                + picking_date + " TEXT,"
                + picking_date_done + " TEXT,"
                + picking_location_src + " TEXT,"
                + picking_location_dest + " TEXT,"
                + picking_type + " TEXT,"
                + picking_partner + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_PICKINGS_TABLE);

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + tbl_products + "("
                + product_id + " integer primary key  , "
                + product_name + " TEXT,"
                + product_picking_id + " integer,"
                + product_qty + " integer,"
                + product_qty_done + " integer,"
                + product_qty_ordered + " integer);";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_PRODUCT_LOTS_TABLE = "CREATE TABLE " + tbl_product_lots + "("
                + product_lots_name + " TEXT, "
                + product_lots_operation_id + " integer,"
                + product_lots_picking_id + " integer);";
        sqLiteDatabase.execSQL(CREATE_PRODUCT_LOTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tbl_pickings);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tbl_products);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tbl_product_lots);
        onCreate(sqLiteDatabase);
    }

    public void createPicking(PickingModel pickingModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(picking_id, pickingModel.getId());
        values.put(picking_name, pickingModel.getName());
        values.put(picking_origin, pickingModel.getOrigin());
        values.put(picking_min_date, pickingModel.getMin_date());
        values.put(picking_max_date, pickingModel.getMax_date());
        values.put(picking_date, pickingModel.getDate());
        values.put(picking_date_done, pickingModel.getDate_done());
        values.put(picking_location_src, pickingModel.getLocation_src());
        values.put(picking_location_dest, pickingModel.getLocation_dest());
        values.put(picking_type, pickingModel.getPicking_type());
        values.put(picking_partner, pickingModel.getPartner());

        // Inserting Row
        db.insert(tbl_pickings, null, values);
        db.close(); // Closing database connection
    }

    public void createProducts(ProductModel productModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(product_id, productModel.getId());
        values.put(product_name, productModel.getName());
        values.put(product_picking_id, productModel.getPicking());
        values.put(product_qty, productModel.getProduct_qty());
        values.put(product_qty_done, productModel.getQty_done());
        values.put(product_qty_ordered, productModel.getQty_ordered());

        // Inserting Row
        db.insert(tbl_products, null, values);
        db.close(); // Closing database connection
    }

    public void createProductLots(ProductLotsModel productLotsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(product_lots_name, productLotsModel.getName());
        values.put(product_lots_operation_id, productLotsModel.getOperation());
        values.put(product_lots_picking_id, productLotsModel.getPicking());
        // Inserting Row
        db.insert(tbl_product_lots, null, values);
        db.close(); // Closing database connection
    }

    public PickingModel getPicking(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        PickingModel tempPickingModel = new PickingModel();

        String query = "SELECT * FROM " + tbl_pickings + " WHERE " +
                picking_id + "=" + id.toString();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            tempPickingModel.setId(cursor.getInt(0));
            tempPickingModel.setName(cursor.getString(1));
            tempPickingModel.setOrigin(cursor.getString(2));
            tempPickingModel.setMin_date(cursor.getString(3));
            tempPickingModel.setMax_date(cursor.getString(4));
            tempPickingModel.setDate(cursor.getString(5));
            tempPickingModel.setDate_done(cursor.getString(6));
            tempPickingModel.setLocation_src(cursor.getString(7));
            tempPickingModel.setLocation_dest(cursor.getString(8));
            tempPickingModel.setPicking_type(cursor.getString(9));
            tempPickingModel.setPartner(cursor.getString(10));
        }

        return tempPickingModel;
    }

    public List<ProductModel> getPickingProducts(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<ProductModel> tempProductModelList = new ArrayList<ProductModel>();

        String query = "SELECT * FROM " + tbl_products + " WHERE " +
                product_picking_id + "=" + id.toString();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(cursor.getInt(0));
                productModel.setName(cursor.getString(1));
                productModel.setPicking(cursor.getInt(2));
                productModel.setProduct_qty(cursor.getInt(3));
                productModel.setQty_done(cursor.getInt(4));
                productModel.setQty_ordered(cursor.getInt(5));


                tempProductModelList.add(productModel);
            } while (cursor.moveToNext());
        }

        return tempProductModelList;
    }

    public Boolean existingProductLot(String name, String operationId, String pickingId) {
        SQLiteDatabase db = this.getWritableDatabase();

        Boolean existing = false;

        String query = "SELECT " + product_lots_name + " FROM " + tbl_product_lots + " WHERE " +
                product_lots_name + "='" + name + "' AND " +
                product_lots_operation_id + "=" + operationId + " AND " +
                product_lots_picking_id + "=" + pickingId;

        Cursor cursor = db.rawQuery(query, null);

        if ((cursor.moveToFirst()) || cursor.getCount() != 0) {
            cursor.moveToFirst();
            existing = true;
        }

        return existing;
    }

    public void saveProductLot(ProductLotsModel tempProductLotsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(product_lots_name, tempProductLotsModel.getName());
        values.put(product_lots_operation_id, tempProductLotsModel.getOperation());
        values.put(product_lots_picking_id, tempProductLotsModel.getPicking());

        // Inserting Row
        db.insert(tbl_product_lots, null, values);
        db.close(); // Closing database connection
    }

    public List<BarcodeModel> lotsAsBarcode (String operationId, String pickingId) {
        SQLiteDatabase db = this.getWritableDatabase();

        List<BarcodeModel> barcodeModelList = new ArrayList<BarcodeModel>();

        String query = "SELECT * FROM " + tbl_product_lots + " WHERE " +
                product_lots_operation_id + "=" + operationId + " AND " +
                product_lots_picking_id + "=" + pickingId;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                BarcodeModel barcodeModel = new BarcodeModel();
                barcodeModel.setValue(cursor.getString(0));
                barcodeModel.setProduct_id(cursor.getString(1));

                barcodeModelList.add(barcodeModel);
            } while (cursor.moveToNext());
        }

        return barcodeModelList;
    }

    public void wipeDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_PICKINGS = "DELETE FROM " + tbl_pickings;
        String DELETE_PRODUCTS = "DELETE FROM " + tbl_products;
        String DELETE_PRODUCT_LOTS = "DELETE FROM " + tbl_product_lots;

        db.execSQL(DELETE_PICKINGS);
        db.execSQL(DELETE_PRODUCTS);
        db.execSQL(DELETE_PRODUCT_LOTS);
        db.close();
    }

    public List<PickingModel> getAllPickings() {
        List<PickingModel> pickingModelList  = new ArrayList<PickingModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tbl_pickings;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PickingModel pickingModel = new PickingModel();
                pickingModel.setId(cursor.getInt(0));
                pickingModel.setName(cursor.getString(1));
                pickingModel.setOrigin(cursor.getString(2));
                pickingModel.setMin_date(cursor.getString(3));
                pickingModel.setMax_date(cursor.getString(4));
                pickingModel.setDate(cursor.getString(5));
                pickingModel.setDate_done(cursor.getString(6));
                pickingModel.setLocation_src(cursor.getString(7));
                pickingModel.setLocation_dest(cursor.getString(8));
                pickingModel.setPicking_type(cursor.getString(9));
                pickingModel.setPartner(cursor.getString(10));


                pickingModelList.add(pickingModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return pickingModelList;
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> productModelList  = new ArrayList<ProductModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tbl_products;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductModel productModel = new ProductModel();
                productModel.setId(cursor.getInt(0));
                productModel.setName(cursor.getString(1));
                productModel.setPicking(cursor.getInt(2));
                productModel.setProduct_qty(cursor.getInt(3));
                productModel.setQty_done(cursor.getInt(4));
                productModel.setQty_ordered(cursor.getInt(5));

                productModelList.add(productModel);
            } while (cursor.moveToNext());
        }

        db.close();
        return productModelList;
    }

    public List<ProductLotsModel> getAllProductLots() {
        List<ProductLotsModel> productLotsModelList  = new ArrayList<ProductLotsModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tbl_product_lots;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductLotsModel productLotsModel = new ProductLotsModel();
                productLotsModel.setName(cursor.getString(0));
                productLotsModel.setOperation(cursor.getInt(1));
                productLotsModel.setPicking(cursor.getInt(2));

                productLotsModelList.add(productLotsModel);
            } while (cursor.moveToNext());
        }
        // return quote list

        db.close();
        return productLotsModelList;
    }
}