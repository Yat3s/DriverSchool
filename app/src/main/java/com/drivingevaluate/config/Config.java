package com.drivingevaluate.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.drivingevaluate.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class Config {

    /** 保存是否是第一次运行程序的标记 */
    public final static String IS_FIRST_RUN = "isFirstRun";
    /** 判断用户是否登录 标记 */
    public final static String IS_LOGIN = "isLogin";
    /** 不是第一次运行标识 */
    public final static int NOT_FIRST = 1;
    /** 是第一次运行标识 */
    public final static int IS_FIRST = -1;
    /** 已登录标示 */
    public final static int HAS_LOGIN = 1;
    /** 获取数据默认条数 **/
    public static final int pageSize = 8;
    public static String UPDATE_SAVENAME = "jkdp";
    // 网络交互地址前段
    public static String HTTPURL1 = "http://123.56.42.37/dianping";
    public static String HTTPURL2 = "http://172.10.1.100:8080/dianping";
    public static String HTTPURL = "http://192.168.1.108:8080/dianping";
    public static String DSchoolURL = "http://123.56.42.37/dianping/api/merchant/get_list.htm";
    public static String NewsURL = "http://123.56.42.37/dianping/api/publish/get_list.htm";
    public static String CoachURL = "http://123.56.42.37/dianping/api/goods/get_merchant_goods.htm";
    public static String AddNewURL = "http://123.56.42.37/dianping/api/publish/commit_publish.htm";
    public static String LoginURL = "http://123.56.42.37/dianping/api/user/login.htm";
    public static String CommentURL = "http://123.56.42.37/dianping/api/comment/get_comments.htm";

    public static String imgUploadUrl1 = "http://123.56.42.37/dianping/upload.htm?tag=1";
    public static String imgUploadUrl = "http://192.168.1.108:8080/image_center/upload.htm?tag=1";
    // 店铺列表图片前段地址
    public static String SHOPLISTIMGURL = HTTPURL1 + "/files/photo/";
    // 商品详情页地址
    public static String GOODS_DETAIL_PATH = HTTPURL + "/goods/goods_detai.htm";

    // 第一个listview的文本数据数组
    public static String[] LISTVIEWTXT = new String[] { "热门分类" };
    // 第二个listview的文本数据
    public static String[][] MORELISTTXT = { { "全部分类", "全部驾校" } };
    // shoplist中toplist文本
    public static String[] SHOPLIST_TOPLIST = new String[] { "全部驾校", "团购驾校" };
    // shoplist中排序文本
    public static String[] SHOPLIST_THREELIST = { "默认排序", "距离最近", "价格最低", "价格最高" };
    // shoplist中商区文本
    public static String[] SHOPLIST_PLACE = new String[] { "附近" };
    // 美食全部地区数组2
    public static String[][] SHOPLIST_PLACESTREET = new String[][] { { "500米", "1000米", "2000米", "5000米" } };
    public static DisplayImageOptions ImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_default)
            .showImageOnFail(R.mipmap.ic_default).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
}
