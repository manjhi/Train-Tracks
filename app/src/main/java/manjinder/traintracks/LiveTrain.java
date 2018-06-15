package manjinder.traintracks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LiveTrain extends AppCompatActivity {
    EditText TrainNo,LiveDate;
    Button SearchLive;
    String Train_No,Live_Date;
    ProgressDialog dialog;
    TextView train_name,Station_Name,SchArr,ActArr,SchDep,ActDep,delay,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train);
        dialog = new ProgressDialog(this);

        TrainNo=(EditText)findViewById(R.id.LiveTrainNo);
        LiveDate=(EditText)findViewById(R.id.LiveTrainDate);
        train_name=(TextView)findViewById(R.id.train_name);
        Station_Name=(TextView)findViewById(R.id.StationName);
        SchArr=(TextView)findViewById(R.id.ScharrTime);
        ActArr=(TextView)findViewById(R.id.SchActArrTime);
        SchDep=(TextView)findViewById(R.id.SchdepTime);
        ActDep=(TextView)findViewById(R.id.SchActDepTime);
        delay=(TextView)findViewById(R.id.Delay);
        status=(TextView)findViewById(R.id.Status);

        SearchLive=(Button)findViewById(R.id.TrainLiveStatus);

        SearchLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Train_No=TrainNo.getText().toString();
                Live_Date=LiveDate.getText().toString();
                new LiveRequest().execute();
            }
        });
    }
    public class LiveRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Please wait....");
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String aVoid) {

            System.out.println("Live Train data"+aVoid);
            dialog.dismiss();
            try {
                JSONObject Live = new JSONObject(aVoid);
                if(Live.getInt("response_code")==200){
                    train_name.setVisibility(View.VISIBLE);
                    Station_Name.setVisibility(View.VISIBLE);
//                    SchArr.setVisibility(View.VISIBLE);
//                    ActArr.setVisibility(View.VISIBLE);
//
//                    SchDep.setVisibility(View.VISIBLE);
//                    ActDep.setVisibility(View.VISIBLE);
//                    delay.setVisibility(View.VISIBLE);
                    status.setVisibility(View.VISIBLE);
//                    System.out.println("Train Name is="+Live.getJSONObject("train").get("name").toString());
//
                    train_name.setText("Train Name:"+Live.getJSONObject("train").get("name").toString());
                    Station_Name.setText("Current Station:"+Live.getJSONObject("current_station").getString("name"));
//                    SchArr.setText("Schedule Arrival:"+Live.getJSONObject("current_station").get("scharr").toString());
//                    ActArr.setText("Acepted Arrival:"+Live.getJSONObject("current_station").get("actarr").toString());
//                    SchDep.setText("Schedule Departure:"+Live.getJSONObject("current_station").get("schdep").toString());
//                    ActDep.setText("Acepted Departure:"+Live.getJSONObject("current_station").get("actdep").toString());
//                    delay.setText("Delay:"+Live.getJSONObject("current_station").get("status").toString());
                    status.setText("Live Status:"+Live.get("position").toString());


// train_name.setText(Live.get("position").toString());
//
//                    for(int i=0;i< train.getJSONArray("route").length();i++){
//                        JSONObject current=(JSONObject) train.getJSONArray("route").get(i);
//                        route_list.add(new Route(current.getString("fullname"),current.getString("schdep"),current.getString("scharr")));
//                    }
//                    Route_Adapter route_adapter=new Route_Adapter(TrainInfoByNumber.this,route_list);
//                    ListView route_view=(ListView)findViewById(R.id.route_view);
//                    route_view.setAdapter(route_adapter);
                }
                else{
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
                URL url = new URL("http://api.railwayapi.com/v2/live/train/"+Train_No+"/date/"+Live_Date+"/apikey/wal8yhek39/");
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
