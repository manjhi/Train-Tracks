package manjinder.traintracks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class TrainBetweenStation extends AppCompatActivity {
    ProgressDialog dialog;
    EditText Source,Destintion,Date;
    String source,destintion,date;
    Button StationSearch;

    ArrayList<Stations> station_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_station);
        dialog = new ProgressDialog(this);

        Source=(EditText)findViewById(R.id.source);
        Destintion=(EditText)findViewById(R.id.destini);
        Date=(EditText)findViewById(R.id.stndate);

        StationSearch=(Button)findViewById(R.id.showstation);

        station_list=new ArrayList<>();
        StationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source=Source.getText().toString();
                destintion=Destintion.getText().toString();
                date=Date.getText().toString();
                new StationRequest().execute();
            }
        });
    }


    public class StationRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Please wait....");
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String aVoid) {

            System.out.println("Stations"+ aVoid);
            dialog.dismiss();
            try {
                JSONObject stations = new JSONObject(aVoid);

                if(stations.getInt("response_code")==200){
//                    train_name.setVisibility(View.VISIBLE);
//                    date_show.setVisibility(View.VISIBLE);
//                    avail_show.setVisibility(View.VISIBLE);
//                    train_name.setText(seat.get("train_name").toString());
////
                    for(int i=0;i< stations.getJSONArray("trains").length();i++){
                        JSONObject current=(JSONObject) stations.getJSONArray("trains").get(i);
                        station_list.add(new Stations(current.getString("number"),current.getString("name"),current.getString("src_departure_time"),current.getString("dest_arrival_time")));
//                        System.out.println("Status is"+seat.getJSONArray("availability").toString());
                    }
                    Stations_Adapter stations_adapter=new Stations_Adapter(TrainBetweenStation.this,station_list);
                    ListView seat_view=(ListView)findViewById(R.id.StationsName);
                    seat_view.setAdapter(stations_adapter);
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
                URL url = new URL("http://api.railwayapi.com/v2/between/source/"+source+"/dest/"+destintion+"/date/"+date+"/apikey/wal8yhek39/");
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
