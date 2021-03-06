package hey.rich.edmontonwifi.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import hey.rich.edmontonwifi.EdmontonWifi;
import hey.rich.edmontonwifi.Objects.Construction;
import hey.rich.edmontonwifi.Objects.ConstructionList;
import hey.rich.edmontonwifi.R;
import hey.rich.edmontonwifi.activities.ConstructionViewActivity;
import hey.rich.edmontonwifi.adapters.ConstructionArrayAdapter;

/**
 * Created by chris on 14/08/14.
 */
public class ConstructionFragment extends Fragment {

    private final static String TAG = ConstructionFragment.class.getName();
    private List<Construction> constructions;
    private ConstructionArrayAdapter adapter;


    public ConstructionFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_construction, container, false);

        setupList(rootView);

        return rootView;
    }

    private void updateLocation(){
        Location l = EdmontonWifi.getLocation(getActivity());
        if(l == null){
            return;
        }
        Toast.makeText(getActivity(), "Getting location", Toast.LENGTH_SHORT).show();

        for(Construction construction: constructions) construction.setDistanceToLocation(l);
    }

    private void setupList(View v){
        ListView lView = (ListView) v.findViewById(R.id.construction_fragment_listview);
        ConstructionList constructionList = EdmontonWifi.getConstructionList(getActivity());
        constructions = constructionList.getAllConstructions();

        adapter = new ConstructionArrayAdapter(getActivity(), constructions);
        lView.setAdapter(adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: make something happen on click
                Intent i = new Intent(getActivity(), ConstructionViewActivity.class);
                i.putExtra(ConstructionViewActivity.CONSTRUCTION_ID, position);
                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Error trying to Open, Try Again Later",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateLocation();
    }

}
