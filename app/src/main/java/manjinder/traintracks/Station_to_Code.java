package manjinder.traintracks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Station_to_Code extends AppCompatActivity {
    EditText StationName;
    Button GETCODE;
    TextView StationCode;
    ProgressDialog dialog;

    String Station_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_to__code);
        dialog = new ProgressDialog(this);


        StationName=(EditText)findViewById(R.id.Station_Name);
        GETCODE=(Button)findViewById(R.id.Get_code);
        StationCode=(TextView)findViewById(R.id.getcode);

        GETCODE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Station_Name= String.valueOf(StationName.getText());
                new StationCode().execute();
            }
        });

    }


    public class StationCode extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {


            dialog.setMessage("Please wait....");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {


            System.out.println("Station Code Data"+s);
            Log.d("Ans",s);
            dialog.dismiss();
            try {
                JSONObject Code=new JSONObject(s);
                if (Code.getInt("response_code")==200){
                    Log.d("Responce","pass");
                    for(int i=0;i< Code.getJSONArray("stations").length();i++){

                        JSONObject current=(JSONObject) Code.getJSONArray("stations").get(i);
                        Log.d("Responce",current.getString("code"));
                        if (Station_Name.toUpperCase().equals(current.getString("name"))){
                            Log.d("code",current.getString("name"));
                            StationCode.setText(current.getString("code"));
                        }
                    }
                }else {
                    Log.d("Responce","Fail");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection connection;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL("https://api.railwayapi.com/v2/name-to-code/station/"+Station_Name+"/apikey/wal8yhek39/");
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
