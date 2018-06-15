package manjinder.traintracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Manjinder_Singh on 30-Aug-17.
 */
public class Seat_Adapter extends ArrayAdapter<Seat> {
    public Seat_Adapter(Context context, List<Seat> objects) {
        super(context, 0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Seat seat=getItem(position);

        if (convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.seat_info,parent,false);

            TextView seatdate=(TextView)convertView.findViewById(R.id.seatDate);
            TextView seatstatus=(TextView)convertView.findViewById(R.id.seatStatus);

            seatdate.setText(seat.getDate());
            seatstatus.setText(seat.getStatus());



        return convertView;
    }
}
