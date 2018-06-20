package ph.com.justin.odoozebra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    List<PickingModel> pickingModelList;
    PickingAdapter pickingAdapter;
    String connectivity = "Not Connected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        recyclerView = findViewById(R.id.recycler_view_pickings);



        loadPickings();
        new SyncPickings(MainActivity.this).execute("");
    }

    public void loadPickings() {
        pickingModelList = databaseHelper.getAllPickings();

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
            String json = "0";
            String tempNum;

            try {
                JSONArray pickings = new JSONArray(wr.makeWebServiceCall("http://192.168.88.44:8000/pickings/", WebRequest.GET));
                connectivity = "Connected";

                Log.e("List of students" , pickings.toString());
                for (int a = 0 ; a < pickings.length() ; a++){

                    JSONObject pickingObject = pickings.getJSONObject(a);

                    PickingModel pickingModel = new PickingModel();
                    pickingModel.setId(Integer.parseInt(pickingObject.getString("id")));
                    pickingModel.setName(pickingObject.getString("name"));

                    databaseHelper.createPicking(pickingModel);
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

            pickingAdapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }
}
