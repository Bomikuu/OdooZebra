package ph.com.justin.odoozebra;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListLotsActivity extends AppCompatActivity {

    DataAdapter adapter = null;
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView = null;
    List<BarcodeModel> tempBarcodeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lots);

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);

        databaseHelper = new DatabaseHelper(ListLotsActivity.this);

        loadLotsView();

        new SyncLots(ListLotsActivity.this).execute("");
    }

    public void loadLotsView()  {
        adapter = new DataAdapter(ModGlobal.barcodeModels);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    class SyncLots extends AsyncTask<String, String, String> {
        private Context context;
        ProgressDialog progressDialog;

        private SyncLots(Context c) {
            this.context = c;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Downloading packing lots");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            progressDialog.setProgress(Integer.parseInt(values[0]));

            progressDialog.setMessage("Downloading pickings " + values[0] + "%");
        }

        @Override
        protected String doInBackground(String... params) {
            String temp = "0";

            tempBarcodeList = new ArrayList<BarcodeModel>();

            tempBarcodeList = databaseHelper.lotsAsBarcode(ModGlobal.currentProductID.toString(),
                    ModGlobal.currentPickingID.toString());

            return temp;
        }


        @Override
        protected void onPostExecute(String strFromDoInBg) {
            super.onPostExecute("");
            ModGlobal.barcodeModels.clear();

            loadLotsView();

            ModGlobal.barcodeModels.addAll(tempBarcodeList);

            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }
}
