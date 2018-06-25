package ph.com.justin.odoozebra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductModel> productModels;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onLongItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtQuantityOrdered, txtQuantityToDo, txtQuantityDone, txtProgress;
        ProgressBar progressQuantity;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            txtProductName = view.findViewById(R.id.txtProductName);
            txtQuantityOrdered = view.findViewById(R.id.txtQuantityOrdered);
            txtProgress = view.findViewById(R.id.txtProgress);
            progressQuantity = view.findViewById(R.id.progressQuantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongItemClick(position);
                        }
                    }

                    return true;
                }
            });
        }
    }

    public ProductAdapter(Context mContext, List<ProductModel> productModels) {
        this.mContext = mContext;
        this.productModels = productModels;
    }


    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_details, parent, false);

        return new MyViewHolder(itemView, mListener);
    }


    @Override
    public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, int position) {
        holder.txtProductName.setText(productModels.get(position).getName());
        holder.txtQuantityOrdered.setText("Quantity Ordered:" + productModels.get(position).getQty_ordered());
        holder.txtProgress.setText(productModels.get(position).getQty_done() + "/" + productModels.get(position).getProduct_qty() );
        int progressQty = productModels.get(position).getQty_done() / productModels.get(position).getProduct_qty();
        holder.progressQuantity.setProgress(progressQty);
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }
}


