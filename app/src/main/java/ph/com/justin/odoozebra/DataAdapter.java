package ph.com.justin.odoozebra;


import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by irvin on 10/26/16.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<BarcodeModel> form;
    private BitmapEncoder bitmapEncoder;
    public DataAdapter(List<BarcodeModel> form) {
        this.form = form;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.barcode_list, viewGroup, false);
        bitmapEncoder = new BitmapEncoder();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.barcodeNumber.setText(form.get(i).getValue());

        Bitmap bitmap = null;
        try {
            bitmap = bitmapEncoder.encodeAsBitmap(form.get(i).getValue(), BarcodeFormat.CODE_128, 600, 300);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        viewHolder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return form.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView barcodeNumber;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            barcodeNumber =  view.findViewById(R.id.barcodeNumber);
            imageView =   view.findViewById(R.id.imageView);
        }
    }


    public void setItems(List<BarcodeModel> barcodes) {
        this.form = new ArrayList<>();
        this.form = barcodes;
        notifyDataSetChanged();

        for (int i = 0; i < ModGlobal.barcodeModels.size(); i++) {
            Log.e("Test:", ModGlobal.barcodeModels.get(i).getValue());
        }
    }
}