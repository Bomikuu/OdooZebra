package ph.com.justin.odoozebra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPickingDetails extends Fragment {

    View v;
    TextView txtShipmentName, txtCompany, txtDateExpected, txtOrigin, txtDateCreated;
    TextView txtLocationSource, txtLocationDestination, txtPickType;
    DatabaseHelper databaseHelper;
    PickingModel currentPickingModel;

    public FragmentPickingDetails() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.picking_details, container, false);

        txtShipmentName = v.findViewById(R.id.txtShipmentName);
        txtCompany = v.findViewById(R.id.txtCompany);
        txtDateExpected = v.findViewById(R.id.txtDateExpected);
        txtOrigin = v.findViewById(R.id.txtOrigin);
        txtDateCreated = v.findViewById(R.id.txtDateCreated);
        txtLocationSource = v.findViewById(R.id.txtLocationSource);
        txtLocationDestination = v.findViewById(R.id.txtLocationDestination);
        txtPickType = v.findViewById(R.id.txtPickType);

        databaseHelper = new DatabaseHelper(this.getContext());
        currentPickingModel = databaseHelper.getPicking(ModGlobal.currentPickingID);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtCompany.setText(currentPickingModel.getPartner());
        txtShipmentName.setText(currentPickingModel.getName());
        txtDateExpected.setText("Min date:" + currentPickingModel.getMin_date() +
                "\nMax date:" + currentPickingModel.getMax_date());
        txtOrigin.setText("Origin:" + currentPickingModel.getOrigin());
        txtDateCreated.setText("Date Created:" + currentPickingModel.getDate() +
                "\nDate Done:" + currentPickingModel.getDate_done());
        txtLocationSource.setText("Source: " + currentPickingModel.getLocation_src());
        txtLocationDestination.setText("Destination:" + currentPickingModel.getLocation_dest());
        txtPickType.setText("Picking type:" + currentPickingModel.getPicking_type());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*txtShipmentName = v.findViewById(R.id.txtShipmentName);
        txtCompany = v.findViewById(R.id.txtCompany);
        txtDateExpected = v.findViewById(R.id.txtDateExpected);
        txtOrigin = v.findViewById(R.id.txtOrigin);
        txtDateCreated = v.findViewById(R.id.txtDateCreated);
        txtLocationSource = v.findViewById(R.id.txtLocationSource);
        txtLocationDestination = v.findViewById(R.id.txtLocationDestination);
        txtPickType = v.findViewById(R.id.txtPickType);

        txtCompany.setText(ModGlobal.currentPickingID.toString());*/
    }


}
