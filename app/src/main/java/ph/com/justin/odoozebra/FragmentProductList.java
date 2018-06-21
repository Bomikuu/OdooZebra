package ph.com.justin.odoozebra;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentProductList extends Fragment {
    ProductAdapter productAdapter;
    List<ProductModel> productModelList;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    List<ProductModel> currentProductModelList;

    View v;
    public FragmentProductList() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.product_list, container, false);
        recyclerView = v.findViewById(R.id.recycler_view_products);
        currentProductModelList = new ArrayList<ProductModel>();

        databaseHelper = new DatabaseHelper(this.getContext());
        currentProductModelList = databaseHelper.getPickingProducts(ModGlobal.currentPickingID);

        loadProductsView();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public void loadProductsView() {
        productAdapter = new ProductAdapter(this.getContext(), currentProductModelList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onLongItemClick(int position) {
            }
        });
    }

    public void goToProductLots() {
        Intent intent = new Intent(this.getContext(), ProductLotsActivity.class);
        startActivity(intent);
    }
}


