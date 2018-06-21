package ph.com.justin.odoozebra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Integer size;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    List<PickingModel> pickingModelList;
    List<ProductModel> productModelList;
    List<ProductLotsModel> productLotsModelList;
    PickingAdapter pickingAdapter;
    String connectivity = "Not Connected";
    TextView txtCountPickings;
    Boolean resync = false;

    //TabLayout
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCountPickings = findViewById(R.id.txtCountPickings);
        size = 0;
        //TabLayout

        setTitle(null);
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setLogo(R.drawable.ic_android_black_24dp);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        databaseHelper = new DatabaseHelper(MainActivity.this);
        recyclerView = findViewById(R.id.recycler_view_pickings);

        // Create empty list
        initializeLists();

        // Load the recyclerview for the pickings
        loadPickingsView();

        pickingModelList.addAll(databaseHelper.getAllPickings());
    }

    @Override
    protected void onStart() {
        super.onStart();

        txtCountPickings.setText(pickingModelList.size() + " Pickings available");
        pickingAdapter.notifyDataSetChanged();
    }

    public void initializeLists() {
        pickingModelList = new ArrayList<PickingModel>();
        productModelList = new ArrayList<ProductModel>();
        productLotsModelList = new ArrayList<ProductLotsModel>();
    }

    public void loadPickingsView() {
        pickingAdapter = new PickingAdapter(MainActivity.this, pickingModelList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pickingAdapter);

        pickingAdapter.setOnItemClickListener(new PickingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ModGlobal.currentPickingID = pickingModelList.get(position).getId();

                goToProducts();
            }

            @Override
            public void onLongItemClick(int position) {
            }
        });
    }

    public void resyncPickings() {
        resync = true;
        new SyncPickings(MainActivity.this).execute("");
    }

    public void goToProducts() {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        startActivity(intent);
    }

    class SyncPickings extends AsyncTask<String, String, String> {
        WebRequest wr = new WebRequest();
        private Context context;
        ProgressDialog progressDialog;


        private SyncPickings(Context c) {
            this.context = c;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Downloading pickings");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }


        @Override
        protected String doInBackground(String... params) {
            pickingModelList.clear();
            databaseHelper.wipeDatabase();
            String json = "0";
            String tempNum;

            try {
                JSONArray pickings = new JSONArray(wr.makeWebServiceCall("https://api.myjson.com/bins/1121xe", WebRequest.GET));
                connectivity = "Connected";

                Log.e("List of students" , pickings.toString());
                for (int a = 0 ; a < pickings.length() ; a++){

                    JSONObject pickingObject = pickings.getJSONObject(a);

                    PickingModel pickingModel = new PickingModel();
                    pickingModel.setId(Integer.parseInt(pickingObject.getString("id")));
                    pickingModel.setName(pickingObject.getString("name"));
                    pickingModel.setOrigin(pickingObject.getString("origin"));
                    pickingModel.setMin_date(pickingObject.getString("min_date"));
                    pickingModel.setMax_date(pickingObject.getString("max_date"));
                    pickingModel.setDate(pickingObject.getString("date"));
                    pickingModel.setDate_done(pickingObject.getString("date_done"));
                    pickingModel.setLocation_src(pickingObject.getString("location"));
                    pickingModel.setLocation_dest(pickingObject.getString("location_dest"));
                    pickingModel.setPicking_type(pickingObject.getString("picking_type"));
                    pickingModel.setPartner(pickingObject.getString("partner"));

                    databaseHelper.createPicking(pickingModel);
                    pickingModelList.add(pickingModel);

                    JSONArray products = new JSONArray(pickingObject.getString("pack_operations"));

                    for (int b = 0 ; b < products.length() ; b++) {

                        JSONObject productObject = products.getJSONObject(b);

                        ProductModel productModel = new ProductModel();
                        productModel.setId(Integer.parseInt(productObject.getString("id")));
                        productModel.setName(productObject.getString("name"));
                        productModel.setPicking(Integer.parseInt(productObject.getString("picking")));
                        productModel.setProduct_qty(Integer.parseInt(productObject.getString("product_qty")));
                        productModel.setQty_done(Integer.parseInt(productObject.getString("qty_done")));
                        productModel.setQty_ordered(Integer.parseInt(productObject.getString("ordered_qty")));

                        databaseHelper.createProducts(productModel);
                        productModelList.add(productModel);

                        JSONArray product_lots = new JSONArray(productObject.getString("pack_operation_lots"));

                        for (int c = 0 ; c < product_lots.length() ; c++) {

                            JSONObject productLotsObject = product_lots.getJSONObject(c);

                            ProductLotsModel productLotsModel = new ProductLotsModel();
                            productLotsModel.setId(Integer.parseInt(productLotsObject.getString("id")));
                            productLotsModel.setName(productLotsObject.getString("lot_name"));
                            productLotsModel.setOperation(Integer.parseInt(productObject.getString("id")));

                            databaseHelper.createProductLots(productLotsModel);
                            productLotsModelList.add(productLotsModel);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("error on downloading", e.toString());
            }

            return json;
        }


        @Override
        protected void onPostExecute(String strFromDoInBg) {
            super.onPostExecute("");
            txtCountPickings.setText(pickingModelList.size()  + " Pickings available");
            pickingAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            resyncPickings();
        }
        if(id == R.id.action_new){
            Toast.makeText(MainActivity.this, "Create Text", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

//https://api.myjson.com/bins/1121xe c=25
//https://api.myjson.com/bins/sluje c=62