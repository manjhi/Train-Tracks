package manjinder.traintracks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button TrainByNo,SeatAvail,LiveSearch,BetweenTrain,PNRCheck,Station_Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TrainByNo=(Button)findViewById(R.id.TrainNo);
        SeatAvail=(Button)findViewById(R.id.Seat);
        LiveSearch=(Button)findViewById(R.id.LiveStatus);
        BetweenTrain=(Button)findViewById(R.id.BetweenStation);
        PNRCheck=(Button)findViewById(R.id.PNRstatus);
        Station_Code=(Button)findViewById(R.id.StationtoCode);



        TrainByNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TrainInfoByNumber.class);
                startActivity(intent);
            }
        });

        SeatAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SeatAvailable.class);
                startActivity(intent);
            }
        });
        LiveSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LiveTrain.class);
                startActivity(intent);
            }
        });

        BetweenTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TrainBetweenStation.class);
                startActivity(intent);
            }
        });

        PNRCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PNRStatus.class);
                startActivity(intent);
            }
        });
        Station_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Station_to_Code.class);
                startActivity(intent);
            }
        });
    }
}
