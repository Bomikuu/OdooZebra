package ph.com.justin.odoozebra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import org.w3c.dom.Text;

public class ProductLotsActivity extends AppCompatActivity {

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
                                ModGlobal.barcodeModels.add(new BarcodeModel(txtBarText.getText().toString(),
                                        ModGlobal.currentProductID.toString()));
                                loadPreview(txtBarText.getText().toString());
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
        Intent goToListOfLots = new Intent(ProductLotsActivity.this, ListLotsActivity.class);
        startActivity(goToListOfLots);
    }
}
