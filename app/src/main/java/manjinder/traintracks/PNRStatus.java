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
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PNRStatus extends AppCompatActivity {

    String aa;
    ProgressDialog dialog;
    String pnrNoSave;
    Button Check;
    EditText PnrNo;
    TextView TrNo,TrName,PassangerNO,DOJ,PNRClass,FromSt,ToSt;
    ArrayList<PNR> Passanger_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnrstatus);

        dialog = new ProgressDialog(this);

        PnrNo=(EditText)findViewById(R.id.pnrNo);
        Check=(Button)findViewById(R.id.CheckPNR);
        TrNo=(TextView)findViewById(R.id.pnrTrainNo);
        TrName=(TextView)findViewById(R.id.pnrTrainName);
        PassangerNO=(TextView)findViewById(R.id.passangerNo);
        DOJ=(TextView)findViewById(R.id.pnrDateofJournay);
        PNRClass=(TextView)findViewById(R.id.pnrClass);
        FromSt=(TextView)findViewById(R.id.FromStation);
        ToSt=(TextView)findViewById(R.id.ToStation);

        Passanger_list=new ArrayList<>();
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PnrNo.length()==10) {
                    pnrNoSave = PnrNo.getText().toString();
                    new PNRRequest().execute();
                }
                else {
                    Toast.makeText(PNRStatus.this,"Enter 10 Digit PNR No",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public class PNRRequest extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Please wait....");
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String aVoid) {

            System.out.println("PNR Data"+aVoid);
           dialog.dismiss();
            try {
                JSONObject passinfo = new JSONObject(aVoid);
                if(passinfo.getInt("response_code")==200){
                    TrNo.setVisibility(View.VISIBLE);
                    TrName.setVisibility(View.VISIBLE);
                    PassangerNO.setVisibility(View.VISIBLE);
                    DOJ.setVisibility(View.VISIBLE);

                    PNRClass.setVisibility(View.VISIBLE);
                    FromSt.setVisibility(View.VISIBLE);
                    ToSt.setVisibility(View.VISIBLE);
//
                    TrNo.setText("Train No:"+passinfo.get("train_num").toString());
                    TrName.setText("Train Name:"+passinfo.get("train_name").toString());
                    PassangerNO.setText("Total No Of Passangers:"+passinfo.get("total_passengers").toString());
                    DOJ.setText("Date Of Journey:"+passinfo.get("doj").toString());
                    PNRClass.setText("Class:"+passinfo.get("class").toString());
                    FromSt.setText("From:"+passinfo.getJSONObject("from_station").get("name").toString());
                    ToSt.setText("To:"+passinfo.getJSONObject("to_station").get("name").toString());
//

                     aa=passinfo.get("train_num").toString();
                    System.out.println("Train No"+aa);
                    for(int i=0;i< passinfo.getJSONArray("passengers").length();i++){
                        JSONObject current=(JSONObject) passinfo.getJSONArray("passengers").get(i);
                        Passanger_list.add(new PNR(current.getString("no"),current.getString("current_status"),current.getString("booking_status"),current.getString("coach_position")));
                    }
                    PNR_Adapter pnr_adapter=new PNR_Adapter(PNRStatus.this,Passanger_list);
                    ListView pnr_View=(ListView)findViewById(R.id.PassangerList);
                    pnr_View.setAdapter(pnr_adapter);
                }
                else if (passinfo.getInt("response_code")==404){
                    Toast.makeText(PNRStatus.this,"Data couldn’t be fetched. Request couldn’t go through.",Toast.LENGTH_LONG).show();
                    System.out.println("fAIL");
                }
                else if (passinfo.getInt("response_code")==221){
                    Toast.makeText(PNRStatus.this,"Invalid PNR",Toast.LENGTH_LONG).show();
                    System.out.println("fAIL");
                }
                else if (passinfo.getInt("response_code")==220){
                    Toast.makeText(PNRStatus.this,"Flushed PNR",Toast.LENGTH_LONG).show();
                    System.out.println("fAIL");
                }

                else {
                    Toast.makeText(PNRStatus.this,"Error",Toast.LENGTH_LONG).show();

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
                URL url = new URL("http://api.railwayapi.com/v2/pnr-status/pnr/"+pnrNoSave+"/apikey/wal8yhek39/");
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
