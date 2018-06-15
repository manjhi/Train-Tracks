package manjinder.traintracks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SeatAvailable extends AppCompatActivity {
    ProgressDialog dialog;

    EditText TrainNo,Source,Desti,jdate,seatclass,seatquota;
    String train_no,source,desti,j_date,seat_class,seat_quota;
    Button show;
    TextView train_name,date_show,avail_show;

    ArrayList<Seat> seat_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_available);
        dialog = new ProgressDialog(this);
        TrainNo=(EditText)findViewById(R.id.train_no);
        Source=(EditText)findViewById(R.id.source);
        Desti=(EditText)findViewById(R.id.destination);
        jdate=(EditText)findViewById(R.id.jurnydate);
        seatclass=(EditText)findViewById(R.id.seatclass);
        seatquota=(EditText)findViewById(R.id.quota);
        train_name=(TextView)findViewById(R.id.train_name);
        date_show=(TextView)findViewById(R.id.dateshow);
        avail_show=(TextView)findViewById(R.id.availshow);


        show=(Button)findViewById(R.id.showavailable);

        seat_list=new ArrayList<>();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                train_no=TrainNo.getText().toString();
                source=Source.getText().toString();
                desti=Desti.getText().toString();
                j_date=jdate.getText().toString();
                seat_class=seatclass.getText().toString();
                seat_quota=seatquota.getText().toString();
                new SeatRequest().execute();
            }
        });
    }


    public class SeatRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Please wait....");
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String aVoid) {

            System.out.println("value of seat"+ aVoid);
            dialog.dismiss();
            try {
                JSONObject seat = new JSONObject(aVoid);

                if(seat.getInt("response_code")==200){
                    train_name.setVisibility(View.VISIBLE);
                    date_show.setVisibility(View.VISIBLE);
                    avail_show.setVisibility(View.VISIBLE);
                    train_name.setText(seat.getJSONObject("train").getString("name"));
//
                    for(int i=0;i< seat.getJSONArray("availability").length();i++){
                        JSONObject current=(JSONObject) seat.getJSONArray("availability").get(i);
                        seat_list.add(new Seat(current.getString("date"),current.getString("status")));
                        System.out.println("Status is"+seat.getJSONArray("availability").toString());
                    }
                    Seat_Adapter seat_adapter=new Seat_Adapter(SeatAvailable.this,seat_list);
                    ListView seat_view=(ListView)findViewById(R.id.seatcheck);
                    seat_view.setAdapter(seat_adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Can't Find",Toast.LENGTH_LONG).show();
                    System.out.println("fAIL");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection connection;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL("http://api.railwayapi.com/v2/check-seat/train/"+train_no+"/source/"+source+"/dest/"+desti+"/date/"+j_date+"/class/"+seat_class+"/quota/"+seat_quota+"/apikey/wal8yhek39/");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String data;
                while((data = bufferedReader.readLine()) != null) {
                    stringBuilder.append(data);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }
}
