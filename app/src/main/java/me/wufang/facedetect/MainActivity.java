package me.wufang.facedetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.aip.face.AipFace;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //设置APPID/AK/SK
    public static final String APP_ID = "11044996";
    public static final String API_KEY = "rYZFT2hFcjl86BFPsQo35Ydz";
    public static final String SECRET_KEY = "PiwaMGt88Ycbfbdpto2tlE4dZlPEpcsY";
    TextView response;//响应

    //拍照
    public static final int TAKE_PHOTO = 1;
    private ImageView picture;
    private Uri imageUri;
    //拍照
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        response= (TextView) findViewById(R.id.response);

        Button takePhoto = (Button) findViewById(R.id.take_photo);
        picture = (ImageView) findViewById(R.id.picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 创建File对象， 用于存储拍照后的图片
                File outputImage = new File(getExternalCacheDir(),
                        "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MainActivity.this,
                            "com.example.cameraalbumtest.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });


        DataRequest request=new DataRequest();
        request.execute();





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private class DataRequest extends AsyncTask<String,Void,Void>{
        JSONObject res;
        @Override
        protected Void doInBackground(String... params) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            response.setText(res.toString());
        }
    }
}
