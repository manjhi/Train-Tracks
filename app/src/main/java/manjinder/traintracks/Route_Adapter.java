package manjinder.traintracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayDeque;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Manjinder_Singh on 30-Aug-17.
 */
public class Route_Adapter extends ArrayAdapter<Route> {

    public Route_Adapter(Context context, List<Route> objects) {
        super(context, 0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Route route=getItem(position);
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.route_info,parent,false);

        TextView fullName=(TextView)convertView.findViewById(R.id.full_name);
        TextView sch_arr=(TextView)convertView.findViewById(R.id.sch_arr);
        TextView sch_dep=(TextView)convertView.findViewById(R.id.sch_dep);

        fullName.setText(route.getSta_name());
        sch_arr.setText(route.getArr());
        sch_dep.setText(route.getDep());

        return convertView;
    }
}
