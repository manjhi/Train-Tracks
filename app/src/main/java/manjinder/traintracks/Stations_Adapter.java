package manjinder.traintracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Manjinder_Singh on 02-Sep-17.
 */
public class Stations_Adapter extends ArrayAdapter<Stations> {


    public Stations_Adapter(Context context, List<Stations> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stations stations=getItem(position);
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trains_bt_stations, parent, false);
        }
            TextView num=(TextView)convertView.findViewById(R.id.Number);
            TextView nam=(TextView)convertView.findViewById(R.id.Name);
            TextView arriv=(TextView)convertView.findViewById(R.id.arrivalTime);
            TextView depaa=(TextView)convertView.findViewById(R.id.depTime);


        num.setText(stations.getNumber());
        nam.setText(stations.getName());
        arriv.setText(stations.getAr());
        depaa.setText(stations.getDepa());
        return convertView;
    }
}
