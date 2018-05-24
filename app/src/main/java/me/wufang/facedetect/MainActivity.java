package me.wufang.facedetect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import me.wufang.facedetect.net.DataRequest;

public class MainActivity extends AppCompatActivity {

    TextView response;//响应

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        response= (TextView) findViewById(R.id.response);



        Handler myHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        response.setText(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            }
        };
        DataRequest request=new DataRequest(myHandler);
        request.execute();
    }
}
