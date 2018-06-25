package ph.com.justin.odoozebra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPickingDetails extends Fragment {

    View v;
    TextView txtShipmentName, txtCompany, txtDateExpected, txtOrigin, txtDateCreated, txtDateTransfer;
    TextView txtLocationSource, txtLocationDestination, txtPickType;
    DatabaseHelper databaseHelper;
    PickingModel currentPickingModel;

    public FragmentPickingDetails() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.picking_details, container, false);

        txtDateExpected = v.findViewById(R.id.txtDateExpected);
        txtDateCreated = v.findViewById(R.id.txtDateCreated);
        txtDateTransfer = v.findViewById(R.id.txtDateTransfer);
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
        txtDateExpected.setText("Min date:\n" + currentPickingModel.getMin_date() +
                "\nMax date:\n" + currentPickingModel.getMax_date());
        //txtOrigin.setText("Origin:" + currentPickingModel.getOrigin());
        txtDateCreated.setText(Html.fromHtml("<b> Date Created </b>:") + "\n"  +currentPickingModel.getDate());
        txtDateTransfer.setText("Date of Transfer: \n"  + currentPickingModel.getDate_done());
        txtLocationSource.setText("Source: \n" + currentPickingModel.getLocation_src());
        txtLocationDestination.setText("Destination:\n" + currentPickingModel.getLocation_dest());
        txtPickType.setText("Picking type:\n" + currentPickingModel.getPicking_type());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
