package ph.com.justin.odoozebra;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPickingDetails extends Fragment {

    View v;
    TextView txtSomething;

    public FragmentPickingDetails() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.picking_details, container, false);

        txtSomething = v.findViewById(R.id.txtSomething);




        return v;
    }
}
