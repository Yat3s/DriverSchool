package com.drivingevaluate.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.drivingevaluate.config.Config;
import com.drivingevaluate.model.Comment;
import com.drivingevaluate.model.Good;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.model.Publish;
import com.drivingevaluate.model.User;
import com.drivingevaluate.util.DateUtils;
import com.drivingevaluate.util.HttpUtil;
import com.drivingevaluate.util.MyGet;

public class Api {
    // 用户资料文件夹
    private static final String PROFILE = "profile/";
    // 用户状态文件夹
    private static final String STATUS = "status/";
    //登录
    private static final String userLoginAPI = "/api/user/login.htm";
    //获取商户列表
    private static final String merchantListAPI = "/api/merchant/get_list.htm";
    //获取发表列表
    private static final String publishListAPI = "/api/publish/get_list.htm";
    //获得商户的商品
    private static final String goodsOfMerchantAPI = "/api/goods/get_merchant_goods.htm";
    //获得发布详情
    private static final String publishDetailAPI = "/api/publish/get_details.htm";
    //获取发布评论
    private static final String commentsOfPublishAPI = "/api/comment/get_comments.htm";
    //提交发布数据
    private static final String publishCommitAPI = "/api/publish/commit_publish.htm";
    //请求验证码
    private static final String getIdentifyCodeAPI = "/api/user/get_identify_code.htm";
    //用户注册
    private static final String userRegAPI = "/api/user/user_reg.htm";
    //发布评论
    private static final String commitPublishCommentAPI = "/api/comment/commit_comment.htm";
    //商品详情
    private static final String getGoodsDetailsAPI = "/api/goods/get_goods_details.htm";
    //商品简单信息
    private static final String getGoodsSimpleInfoAPI = "/api/goods/get_goods_simple.htm";
    //获取用户账户
    private static final String getUserAccountAPI = "/api/account/get_user_account.htm";
    //提交订单
    private static final String commitBuyOrderAPI = "/api/payment/create_order.htm";

    private static StringBuilder generateParamentersString(StringBuilder sb,String apiPath,Map<String,Object> params){
        if(params!=null && !params.isEmpty()){
            Entry<String, Object> entry = null;
            for(Iterator<Entry<String, Object>> iter=params.entrySet().iterator();iter.hasNext();){
                entry = iter.next();
                if(entry.getKey()!=null && entry.getValue()!=null)
                    sb.append(entry.getKey()).append("=").append(entry.getValue().toString()).append("&");
            }
        }
        return sb;
    }
    private static String buildFullPath(String apiPath,Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append(Config.HTTPURL1).append(apiPath);
        if(params==null){
            return sb.toString();
        }
        sb.append("?");
        return generateParamentersString(sb,apiPath,params).toString();
    }
    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param handler
     */
    public static User userLogin(String username, String password) throws Exception {
        final Map<String, String> param = new HashMap<String, String>();
        param.put("account", username);
        param.put("password", password);
        User user = new User();
        String json = HttpUtil.postRequest(Config.LoginURL, param);
        Log.e("exc", "user---------" + json);
        user = JSON.parseObject(json, User.class);
        return user;
    }

    /**
     * 获得商户列表
     */
    public static List<Merchant> getMerchantList(int order, int pageNo, double lat, double lng) throws Exception {
        List<Merchant> list;
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageSize", "8");
        map.put("areaNO", "");
        map.put("type", "0");
        map.put("orderBy", order + "");
        map.put("pageNo", pageNo + "");
        map.put("lat", lat + "");
        map.put("lon", lng + "");
        String json = HttpUtil.postRequest(Config.DSchoolURL, map);
        Log.e("exc", json);
        list = JSON.parseArray(json, Merchant.class);
        return list;
    }

    public static List<Publish> getNews(String getMore) throws Exception {
        List<Publish> list;
        Map<String, String> map = new HashMap<String, String>();
        map.put("timestamp", DateUtils.getStrDate(new Date()));
        Log.e("time", DateUtils.getDateStr(new Date()));
        map.put("pageSize", "8");
        map.put("lessthen", getMore);
        String json = HttpUtil.postRequest(Config.NewsURL, map);
        Log.e("exc", json);
        list = JSON.parseArray(json, Publish.class);
        return list;
    }

    public static List<Good> getCoach(String merchantId) throws Exception {
        List<Good> list;
        Map<String, String> map = new HashMap<String, String>();
        map.put("merchantId", merchantId);
        map.put("pageSize", "8");
        map.put("pageNo", "1");
        String json = HttpUtil.postRequest(Config.CoachURL, map);
        Log.e("exc", json);
        list = JSON.parseArray(json, Good.class);
        return list;
    }

    public static String AddNews(Map<String, String> param) throws Exception {
        String json = HttpUtil.postRequest(Config.AddNewURL, param);
        Log.e("exc", "AddNews" + json);
        return json;
    }

    public static String getSchoolList() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phoneBrand", "Meizu");
        map.put("platform", "1");
        map.put("phoneVersion", "19");
        map.put("channel", "AppTreasure");
        map.put("lat", "0.0");
        map.put("lng", "0.0");
        map.put("phoneModel", "MX4");
        map.put("versionNumber", "6.1.0");
        map.put("addTime", "1433469333000");
        String json = HttpUtil.postRequest(Config.AddNewURL, map);
        Log.e("exc", "AddNews" + json);
        return json;
    }

    public static List<Comment> getCommentsOfPublish(Map<String, Object> param) throws Exception {
        String json;
        List<Comment> comments = new ArrayList<Comment>();
        String url = JsonResolveUtils.buildFullPath(commentsOfPublishAPI, param);
        json = MyGet.doGet(url);
        Log.e("exc","comment--------"+url);
        Log.e("exc","comment--------"+json);
        comments = JSON.parseArray(json, Comment.class);
        return comments;
    }
}
