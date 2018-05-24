package me.wufang.facedetect.net;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.aip.face.AipFace;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/25.
 * Email:gowufang@gmail.com
 * Description:
 */

public class DataRequest extends AsyncTask<String,Void,String> {
    JSONObject res;
    Handler mHandler;

    public DataRequest(Handler mHandler) {
        this.mHandler = mHandler;
    }

    //设置APPID/AK/SK
    public static final String APP_ID = "11044996";
    public static final String API_KEY = "rYZFT2hFcjl86BFPsQo35Ydz";
    public static final String SECRET_KEY = "PiwaMGt88Ycbfbdpto2tlE4dZlPEpcsY";
    @Override
    protected String doInBackground(String... params) {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");

        String imageType = "URL";
//            String str = "Hello!";
//            String image= Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        String image= "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526897347248&di=8b417c686f3cd1a6c71902dc31867bf7&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2F00e93901213fb80e5a4607153cd12f2eb8389458.jpg";
        // 人脸检测
        res = client.detect(image, imageType, options);
//            Toast.makeText(this, res.toString(), Toast.LENGTH_SHORT).show();
        Log.d("MainAty",res.toString());

        return res.toString();
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        Message msg=mHandler.obtainMessage();
        if (str!=null){
            msg.what=1;
            msg.obj=res;
        }
        mHandler.sendMessage(msg);
    }
}
