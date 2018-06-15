package manjinder.traintracks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TrainInfoByNumber extends AppCompatActivity {
    ProgressDialog dialog;
    String train_No;
    Button show;
    TextView train_name,station,arrival,depa;
    EditText trainNo;
    ArrayList<Route> route_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_info_by_number);

        dialog = new ProgressDialog(this);

        trainNo=(EditText)findViewById(R.id.insert_train);
        show=(Button)findViewById(R.id.show_train);
        train_name=(TextView)findViewById(R.id.train_name);
        station=(TextView)findViewById(R.id.Stn);
        arrival=(TextView)findViewById(R.id.sch_arr);
        depa=(TextView)findViewById(R.id.sch_dep);
        route_list=new ArrayList<>();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                train_No=trainNo.getText().toString();
                new TrainRequest().execute();
            }
        });
    }

    public class TrainRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Please wait....");
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String aVoid) {

            System.out.println("data"+aVoid);
            Log.d("onPostExecute: ",aVoid);
            dialog.dismiss();
            try {
                JSONObject train = new JSONObject(aVoid);
                if(train.getInt("response_code")==200){
                    train_name.setVisibility(View.VISIBLE);
                    station.setVisibility(View.VISIBLE);
                    arrival.setVisibility(View.VISIBLE);
                    depa.setVisibility(View.VISIBLE);

                    train_name.setText(train.getJSONObject("train").get("name").toString());

                    for(int i=0;i< train.getJSONArray("route").length();i++){
                        JSONObject current=(JSONObject) train.getJSONArray("route").get(i);
                        route_list.add(new Route(current.getJSONObject("station").getString("name"),current.getString("schdep"),current.getString("scharr")));
                    }
                    Route_Adapter route_adapter=new Route_Adapter(TrainInfoByNumber.this,route_list);
                    ListView route_view=(ListView)findViewById(R.id.route_view);
                    route_view.setAdapter(route_adapter);
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
                URL url = new URL("http://api.railwayapi.com/v2/route/train/"+train_No+"/apikey/wal8yhek39/");
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
