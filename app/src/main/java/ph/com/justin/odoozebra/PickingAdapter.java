package ph.com.justin.odoozebra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView overflow;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            txtShipment = view.findViewById(R.id.txtShipment);
            txtCompany = view.findViewById(R.id.txtCompany);
            overflow = view.findViewById(R.id.overflow);

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
        holder.txtShipment.setText(pickingModels.get(position).getName());
        holder.txtCompany.setText(pickingModels.get(position).getPartner());
    }

    @Override
    public int getItemCount() {
        return pickingModels.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_picking, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Added to do", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Marked as done", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}


