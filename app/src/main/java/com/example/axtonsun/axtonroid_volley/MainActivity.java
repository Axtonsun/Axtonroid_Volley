package com.example.axtonsun.axtonroid_volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final ImageView imageView = (ImageView)findViewById(R.id.iv);

        //请求队列对象合一缓存所有HTTP请求
        RequestQueue mQueue = Volley.newRequestQueue(this);//用来执行请求的请求队列  this===》context
        /**
         * StringRequest 响应的主体为字符串
         * JsonArrayRequest 发送和接收JSON数组
         * JsonObjectRequest 发送和接收JSON对象
         * ImageRequest 发送和接收Image
         */
        /**
         * GET请求
         * 第一个参数 目标服务器的URL地址
         * 第二个参数 服务器响应成功的回调
         * 第三个参数 服务器响应失败的回调
         */
       /*StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            //添加自己的响应逻辑
                Log.d("TGA", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            //错误处理
                Log.e("TAG", volleyError.getMessage(),volleyError);
            }
        });
        mQueue.add(stringRequest);*/

        //POST请求方法
        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };
        */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(" https://api.heweather.com/x3/citylist?search=allchina&key=12b73649776046a395e43612b15d1f3a",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("TAG",jsonObject.toString());
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG11",volleyError.getMessage(),volleyError);
            }
        });
        mQueue.add(jsonObjectRequest);
        /**
         * 1.图片URL地址
         * 2.图片请求成功的回调
         * 3.允许图片最大的宽度
         * 4.高度
         * 5.图片的颜色属性
         * 6.错误回调
         */
        /*ImageRequest imageRequest = new ImageRequest("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
        });
        mQueue.add(imageRequest);*/
        /*ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        //imageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",listener);
        imageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",listener,200,200);
*/
        //ImageLoader imageLoader = new ImageLoader(mQueue,new BitmapCache());
        //ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        //imageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",listener,200,200);

     /*   ImageLoader imageLoader = new ImageLoader(mQueue,new BitmapCache());
        NetworkImageView networkImageView = (NetworkImageView)findViewById(R.id.network_image_view);
        networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        networkImageView.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",imageLoader);*/

     /*   GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
                "http://www.weather.com.cn/data/sk/101010100.html", Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {
                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                        Log.d("TAG", "city is " + weatherInfo.getCity());
                        Log.d("TAG", "temp is " + weatherInfo.getTemp());
                        Log.d("TAG", "time is " + weatherInfo.getTime());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG", "onErrorResponse: ");
            }
        });
        mQueue.add(gsonRequest);*/
    }
}
