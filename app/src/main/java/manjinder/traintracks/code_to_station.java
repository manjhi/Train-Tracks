package manjinder.traintracks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class code_to_station extends AppCompatActivity {

    EditText code;
    Button getStation;
    TextView Station;
    String getcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_to_station);

        code=(EditText)findViewById(R.id.Code);
        getStation=(Button)findViewById(R.id.getStation);
        Station=(TextView)findViewById(R.id.codeName);

        getStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcode= String.valueOf(code.getText());

            }
        });
    }
    //now station to code using Asynch task
//    public class CodeStation extends AsyncTask<Void,Void,String>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            HttpURLConnection connection;
//            URL url=new URL("");
//            return null;
//        }
//    }
}
