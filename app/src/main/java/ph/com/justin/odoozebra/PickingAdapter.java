package ph.com.justin.odoozebra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PickingAdapter extends RecyclerView.Adapter<PickingAdapter.MyViewHolder> {

    private Context mContext;
    private List<PickingModel> pickingModels;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onLongItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtShipment, txtCompany;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            txtShipment = view.findViewById(R.id.txtShipment);
            txtCompany = view.findViewById(R.id.txtCompany);

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

    public PickingAdapter(Context mContext, List<PickingModel> pickingModels) {
        this.mContext = mContext;
        this.pickingModels = pickingModels;
    }


    @Override
    public PickingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picking_list, parent, false);

        return new MyViewHolder(itemView, mListener);
    }


    @Override
    public void onBindViewHolder(final PickingAdapter.MyViewHolder holder, int position) {
        holder.txtShipment.setText("Transfer Name: "+ pickingModels.get(position).getName());
        holder.txtCompany.setText("ID: " + pickingModels.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return pickingModels.size();
    }
}


