package manjinder.traintracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Manjinder_Singh on 12-Sep-17.
 */
public class PNR_Adapter extends ArrayAdapter<PNR> {
    public PNR_Adapter(Context context, List<PNR> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PNR pnr=getItem(position);
        if (convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.pnr_info,parent,false);

        TextView SerialNo=(TextView)convertView.findViewById(R.id.SerialNO);
        TextView CurrentStatus=(TextView)convertView.findViewById(R.id.CurrentStatus);
        TextView BookingStatus=(TextView)convertView.findViewById(R.id.BookingStatus);
        TextView CoachStatus=(TextView)convertView.findViewById(R.id.CoachPosition);

        SerialNo.setText(pnr.getNo());
        CurrentStatus.setText(pnr.getCurrent());
        BookingStatus.setText(pnr.getBooking());
        CoachStatus.setText(pnr.getCoach());



        return convertView;
    }
}
