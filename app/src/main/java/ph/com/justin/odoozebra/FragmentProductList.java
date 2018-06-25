package ph.com.justin.odoozebra;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentProductList extends Fragment {
    ProductAdapter productAdapter;
    List<ProductModel> productModelList;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    List<ProductModel> currentProductModelList;
    TextView txtCountProducts, btnSortProduct;

    View v;

    //Variables
    Boolean ascending = true;
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

        txtCountProducts = v.findViewById(R.id.txtCountProducts);
        txtCountProducts.setText(currentProductModelList.size() + " Products Available");

        btnSortProduct = v.findViewById(R.id.btnSortProduct);
        btnSortProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(btnSortProduct);
            }
        });

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
        recyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this.getContext(), DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ModGlobal.currentProductID = currentProductModelList.get(position).getId();
                goToProductLots();
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

    private void sortPickings(Boolean value){

        if(value){
            Collections.reverse(currentProductModelList);
        }
        else{
            Collections.reverse(currentProductModelList);
            loadProductsView();
        }


    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(this.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_sort, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_ascending:
                    ascending = true;
                    sortPickings(ascending);
                    return true;
                case R.id.action_descending:
                    ascending = false;
                    sortPickings(ascending);
                    return true;
                default:
            }
            return false;
        }
    }
}


