package ph.com.justin.odoozebra;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPageAdapter adapter;
    DatabaseHelper databaseHelper;
    PickingModel currentPickingModel;

    TextView txtShipmentName, txtCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        txtShipmentName = findViewById(R.id.txtShipmentName);
        txtCompany = findViewById(R.id.txtCompany);

        initializeToolbar();
        initializeTabFragments();

        initializePickingDetails();

    }

    private void initializePickingDetails(){
        databaseHelper = new DatabaseHelper(ProductActivity.this);
        currentPickingModel = databaseHelper.getPicking(ModGlobal.currentPickingID);

        txtCompany.setText(currentPickingModel.getPartner());
        txtShipmentName.setText(currentPickingModel.getName());
    }


    private void initializeTabFragments(){
        //TabLayout
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewPager_id);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentPickingDetails(), "Details");
        adapter.AddFragment(new FragmentProductList(), "Products");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_1);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_2);
    }

    private void initializeToolbar()
    {
        setTitle("Zebra");
        Toolbar topToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setLogo(R.drawable.ic_android_black_24dp);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));
    }
}
