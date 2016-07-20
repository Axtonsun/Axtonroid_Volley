# Axtonroid_Volley

##学习来源
[Android Volley完全解析(一)，初识Volley的基本用法 ](http://blog.csdn.net/guolin_blog/article/details/17482095 "郭霖")  
[Android Volley完全解析(二)，使用Volley加载网络图片 ](http://blog.csdn.net/guolin_blog/article/details/17482165 "郭霖")  
[Android Volley完全解析(三)，定制自己的Request ](http://blog.csdn.net/guolin_blog/article/details/17612763 "郭霖")

###Volley中的RequestQueue和Request
* `RequestQueue` 用来执行请求的请求队列
* `Request` 用来构造一个请求对象
  * `StringResquest` 响应的主体为字符串
  * `JsonArrayRequest` 发送和接收JSON数组
  * `JsonObjectRequest` 发送和接收JSON对象
  * `ImageRequest` 发送和接收Image  
  
##Volley的基本使用
* 创建RequestQueue mQueue
* 构建自己所需的XXXRequest request
* 之后mQueue.add(request);  
  
###StringQueue的用法
####Get请求
* `RequestQueue mQueue = Volley.newRequestQueue(this);//this===》context`
* 
```
       /**      
         * GET请求
         * 第一个参数 目标服务器的URL地址
         * 第二个参数 服务器响应成功的回调
         * 第三个参数 服务器响应失败的回调
         */
       StringRequest stringRequest = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
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
```    
* `mQueue.add(stringRequest);`  

####Post请求
* `RequestQueue mQueue = Volley.newRequestQueue(this);//this===》context`
* 
```
StringRequest stringRequest = new StringRequest(Request.Method.POST, url,  listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };
```
* `mQueue.add(stringRequest);`  

###JsonRequest的用法
* `RequestQueue mQueue = Volley.newRequestQueue(this);//this===》context`
* 
```
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,  
            new Response.Listener<JSONObject>() {  
                @Override  
                public void onResponse(JSONObject response) {  
                    Log.d("TAG", response.toString());  
                }  
            }, new Response.ErrorListener() {  
                @Override  
                public void onErrorResponse(VolleyError error) {  
                    Log.e("TAG", error.getMessage(), error);  
                }  
            }); 
```
* `mQueue.add(jsonObjectRequest);`

##加载网络图片
* ImageRequest
* ImageLoader 
  * 对图片进行缓存
  * 过滤掉重复的链接
  * 避免重复发送请求
* NetworkImageView

###ImageRequest的用法
* `RequestQueue mQueue = Volley.newRequestQueue(this);//this===》context`
* 
```
        /**
         * 1.图片URL地址
         * 2.图片请求成功的回调
         * 3.允许图片最大的宽度
         * 4.高度
         * 如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
         * 指定成0的话就表示不管图片有多大，都不会进行压缩
         * 5.图片的颜色属性
         * ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，
         * 而RGB_565则表示每个图片像素占据2个字节大小
         * 6.错误回调
         */

    ImageRequest imageRequest = new ImageRequest(  
            "http://developer.android.com/images/home/aw_dac.png",  
            new Response.Listener<Bitmap>() {  
                @Override  
                public void onResponse(Bitmap response) {  
                    imageView.setImageBitmap(response);  
                }  
            }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
                @Override  
                public void onErrorResponse(VolleyError error) {  
                    imageView.setImageResource(R.drawable.default_image);  
                }  
            });  
```
* `mQueue.add(imageRequest);`

###ImageLoader的用法
* `RequestQueue mQueue = Volley.newRequestQueue(this);//this===》context`
* 
```
//第一个参数就是RequestQueue对象，第二个参数是一个ImageCache对象
ImageLoader imageLoader = new ImageLoader(mQueue, new ImageCache() {
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
	}

	@Override
	public Bitmap getBitmap(String url) {
		return null;
	}
});
```
* 
```
ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,//用于显示图片的ImageView控件
                                                  R.mipmap.ic_launcher,//加载图片的过程中显示的图片
                                                  R.mipmap.ic_launcher);//加载图片失败的情况下显示的图片
//imageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",listener);
imageLoader.get("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",//图片的URL地址
                                                  listener,//刚刚获得到的ImageListener对象
                                                  200,//图片允许的最大的宽度
                                                  200);//图片允许的最大的高度
```
#####上面的ImageCache对象是一个空的实现，完全没能起到图片缓存的作用
* 新建BitmapCache实现ImageCache接口
```
public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;
	public BitmapCache() {
		int maxSize = 10 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}
	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}
}
```
* `ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());`  

###NetworkImageView的用法
* `RequestQueue mQueue = Volley.newRequestQueue(this);//this===》context`
* `ImageLoader imageLoader = new ImageLoader(mQueue,new BitmapCache());`
* 布局添加文件
```
<com.android.volley.toolbox.NetworkImageView   
        android:id="@+id/network_image_view"  
        android:layout_width="200dp"  
        android:layout_height="200dp"  
        android:layout_gravity="center_horizontal"  
        />  
```
* 然后获取控件实例
  `NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.network_image_view); `
* 
```
networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
networkImageView.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",//图片的URL地址
                 imageLoader);//创建好的ImageLoader对象
```

##自定义GsonRequest
```
public class GsonRequest<T> extends Request<T> {  
  
    private final Listener<T> mListener;  
  
    private Gson mGson;  
  
    private Class<T> mClass;  
  
    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener,  
            ErrorListener errorListener) {  
        super(method, url, errorListener);  
        mGson = new Gson();  
        mClass = clazz;  
        mListener = listener;  
    }  
  
    public GsonRequest(String url, Class<T> clazz, Listener<T> listener,  
            ErrorListener errorListener) {  
        this(Method.GET, url, clazz, listener, errorListener);  
    }  
    
    @Override  
    protected void deliverResponse(T response) {  
        mListener.onResponse(response);  
    }
    
    @Override  
    protected Response<T> parseNetworkResponse(NetworkResponse response) {  
        try {  
            String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers));  
            return Response.success(mGson.fromJson(jsonString, mClass),HttpHeaderParser.parseCacheHeaders(response));  
        } catch (UnsupportedEncodingException e) {  
            return Response.error(new ParseError(e));  
        }  
    }  
} 
```
* 新建Weather类
* 新建WeatherInfo类
* 
```
GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
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
			public void onErrorResponse(VolleyError error) {
				Log.e("TAG", error.getMessage(), error);
			}
		});
```
* `mQueue.add(gsonRequest);`
