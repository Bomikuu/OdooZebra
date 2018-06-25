package ph.com.justin.odoozebra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class ProductLotsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    BitmapEncoder bitmapEncoder;
    Bitmap bitmap;
    Button btnScan;
    Button btnListOfScan;
    EditText txtBarText;
    TextView txtBarcode;
    ImageView imgBarCode;
    boolean indicator = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_lots);

        bitmapEncoder = new BitmapEncoder();
        btnScan = findViewById(R.id.btnScan);
        btnListOfScan = findViewById(R.id.btnListOfScan);
        txtBarcode = findViewById(R.id.txtBarcode);
        txtBarText = findViewById(R.id.txtBarText);
        imgBarCode = findViewById(R.id.imgBarCode);

        databaseHelper = new DatabaseHelper(ProductLotsActivity.this);

        txtBarText.setEnabled(false);
        txtBarText.setText("");

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btnScan.getText().toString().toLowerCase().equals("start scanning")) {
                    txtBarText.setEnabled(true);
                    txtBarText.setText("");
                    btnScan.setText("STOP SCANNING");
                }
                else {
                    txtBarText.setEnabled(false);
                    txtBarText.setText("");
                    btnScan.setText("START SCANNING");
                }
            }
        });

        btnListOfScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListOfLots();
            }
        });

        txtBarText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (indicator && !(txtBarText.getText().toString().equals(null) || txtBarText.getText().toString().equals(""))) {
                    indicator = false;
                    CountDownTimer countDownTimer = new CountDownTimer(100, 100) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            try{
                                String currBarcode = txtBarText.getText().toString();
                                String currProdId = ModGlobal.currentProductID.toString();
                                String currPickId = ModGlobal.currentPickingID.toString();
                                Boolean existing = databaseHelper.existingProductLot(currBarcode, currProdId, currPickId);

                                ProductLotsModel tempProductLotsModel = new ProductLotsModel(currBarcode, Integer.parseInt(currProdId), Integer.parseInt(currPickId));

                                if (existing == false) {
                                    databaseHelper.saveProductLot(tempProductLotsModel);
                                    loadPreview(txtBarText.getText().toString());
                                }
                                else {
                                    Toast.makeText(ProductLotsActivity.this, "Serial number already exists", Toast.LENGTH_SHORT).show();
                                }

                                txtBarText.setText("");
                                indicator = true;
                            }catch (Exception e){

                            }
                        }

                    }.start();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void loadPreview(String currString) throws WriterException {
        bitmap = bitmapEncoder.encodeAsBitmap(currString, BarcodeFormat.CODE_128, 600, 300);
        txtBarcode.setText(currString);
        imgBarCode.setImageBitmap(bitmap);
    }

    public void goToListOfLots() {
        ModGlobal.barcodeModels = databaseHelper.lotsAsBarcode(ModGlobal.currentProductID.toString(), ModGlobal.currentPickingID.toString());
        Intent goToListOfLots = new Intent(ProductLotsActivity.this, ListLotsActivity.class);
        startActivity(goToListOfLots);
    }
}
