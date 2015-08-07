package com.drivingevaluate.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.w3c.dom.Comment;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drivingevaluate.config.Config;
import com.drivingevaluate.config.Constants;
import com.drivingevaluate.model.Goods;
import com.drivingevaluate.model.Merchant;
import com.drivingevaluate.model.Publish;
import com.drivingevaluate.model.User;
import com.drivingevaluate.model.UserAccount;
import com.drivingevaluate.util.AppMethod;
import com.drivingevaluate.util.HttpUtil;
import com.drivingevaluate.util.MyGet;
import com.drivingevaluate.util.MyPost;

/**
 * @fileName JsonResolveUtils.java
 * @package com.immomo.momo.android.util
 * @description Json解析工具类
 * @author 任东卫
 * @email 86930007@qq.com
 * @version 1.0
 */
public class JsonResolveUtils {
    private static ExecutorService threadPools = Executors.newFixedThreadPool(5);;
    // 用户资料文件夹
    private static final String PROFILE = "profile/";
    // 用户状态文件夹
    private static final String STATUS = "status/";
    //登录
    private static final String userLoginAPI = "/api/user/login.htm";
    //获取商户列表
    private static final String merchantListAPI = "/api/merchant/get_list.htm";
    //获得商户详情
    private static final String merchantDetailAPI = "/api/merchant/get_detail.htm";
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
    //提交用户信息
    private static final String commitUserInfoAPI = "/api/user/save_userinfo.htm";
    //获得用户信息
    private static final String getUserInfoAPI = "/api/user/get_userinfo.htm";
    //修改密码
    private static final String modifyPwdAPI = "/api/user/modify_password.htm";
    //获得用户的订单
    private static final String getUserOrdersAPI = "/api/order/get_user_orders.htm" ;
    //点赞
    private static final String publishPariseAPI = "/api/publish/parise.htm" ;
    //订单处理
    private static final String notifyURL = "/api/payment/notify.htm" ;
    //评价
    private static final String goodsJudgeAPI = "/api/goods/judge.htm" ;
    //获取评价
    private static final String getGoodsJudgeAPI = "/api/goods/get_judges.htm" ;

    //http返回状态码的标示
    private static final String RESULT_STATUS = "result_status";
    private static final String EQ = "=";
    private static final String ANDFU = "&";
    private static final String WENHAO = "?";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";

    private static StringBuilder generateParamentersString(StringBuilder sb,String apiPath,Map<String,Object> params){
        if(params!=null && !params.isEmpty()){
            Entry<String, Object> entry = null;
            for(Iterator<Entry<String, Object>> iter=params.entrySet().iterator();iter.hasNext();){
                entry = iter.next();
                if(entry.getKey()!=null && entry.getValue()!=null)
                    sb.append(entry.getKey()).append(EQ).append(entry.getValue().toString()).append(ANDFU);
            }
        }
        return sb;
    }
    static String buildFullPath(String apiPath,Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        sb.append(Config.HTTPURL1).append(apiPath);
        if(params==null){
            return sb.toString();
        }
        sb.append(WENHAO);
        return generateParamentersString(sb,apiPath,params).toString();
    }
    /**
     * 用户登录
     */
    public static void userLogin(String username,String password,final Handler handler) {
        final Map<String,Object> param = new HashMap<String, Object>();
        param.put(ACCOUNT, username);
        param.put(PASSWORD, password);
        new Thread() {
            public void run() {
                try {
                    String json = MyGet.doGet(JsonResolveUtils.buildFullPath(userLoginAPI, param));
                    JSONObject jobj = JSON.parseObject(json);
                    Integer status = (Integer)jobj.get(RESULT_STATUS);
                    if(null !=status){
                        AppMethod.sendMessage(handler, status, Constants.CODE_REFRESH_LOGIN);
                    }else{
                        AppMethod.sendMessage(handler, JSON.parseObject(json, User.class), Constants.CODE_REFRESH_LOGIN);
                    }
                    Log.e("exc", "JsonUser----------"+JSON.parseObject(json, User.class).getAccount());
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                    Log.e("exc", "JsonUser----------"+e.toString());
                }
            }
        }.start();
    }
    /**
     * 请求验证码
     */
    public static void getIdentifyCode(final Map<String,String> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String json = HttpUtil.postRequest(Config.HTTPURL1+getIdentifyCodeAPI, param);
                    Log.e("exc", "IdentifyCode----------"+json);
                    JSONObject jobj = JSON.parseObject(json);
                    Integer status = (Integer)jobj.get(RESULT_STATUS);
                    AppMethod.sendMessage(handler, status, Constants.CODE_IDENTIFY_CODE_REQUEST);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     *用户注册
     */
    public static void userReg(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String json = MyGet.doGet(JsonResolveUtils.buildFullPath(userRegAPI, param));
                    JSONObject jobj = JSON.parseObject(json);
                    Integer status = (Integer)jobj.get(RESULT_STATUS);
                    Log.e("exc", "JsonStatus----------"+status);
                    AppMethod.sendMessage(handler, status, Constants.CODE_USER_ADD_RESPONSE);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 找回密码
     */

    /**
     * 获得商户列表
     */
    public static void getMerchantList(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(merchantListAPI, param);
                    String json = MyGet.doGet(url);
                    List<Merchant> list = JSON.parseArray(json, Merchant.class);
                    AppMethod.sendMessage(handler, list, Constants.CODE_REFRASH_MERCHANT_LIST);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 获得发表列表
     */
    public static void getPublishList(final Map<String,Object> param,final Handler handler) throws Exception{
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(publishListAPI, param);
                    String json = MyGet.doGet(url);
                    List<Publish> list = JSON.parseArray(json, Publish.class);
                    AppMethod.sendMessage(handler, list, Constants.CODE_REFRASH_PUBLISH_LIST);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 获得发布详情
     */
    public static void getPublishById(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(publishDetailAPI, param);
                    String json = MyGet.doGet(url);
                    Publish p = JSON.parseObject(json, Publish.class);
                    AppMethod.sendMessage(handler, p, Constants.CODE_REFRASH_PUBLIC_DETAIL);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 获得发布下的评论
     */
    public static void getCommentsOfPublish(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(commentsOfPublishAPI, param);
                    //Log.d("======url-=======", url);
                    String json = MyGet.doGet(url);
                    //Log.d("------", json);
                    List<Comment> p = JSON.parseArray(json, Comment.class);
                    AppMethod.sendMessage(handler, p, Constants.CODE_REFRASH_COMMENT_LIST);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();

    }
    /**
     * 提交评论
     */
    public static void commitPublishComment(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(commitPublishCommentAPI, null);
                    //Log.d("======url-=======", url);
                    String json = MyPost.doPost(url, param);
                    Log.e("exc", "commitComment---------url:"+url);
                    Log.e("exc", "commitComment---------json:"+json);
                    JSONObject jo = JSON.parseObject(json);
                    if(jo!=null){
                        Integer status = jo.getInteger(RESULT_STATUS);
                        if(status != null)
                            AppMethod.sendMessage(handler, status, Constants.CODE_SHOW_COMMENT_STATUS);
                    }
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();

    }
    /**
     * 获得商户下的商品
     */
    public static void getGoodsOfMerchant(final Map<String,Object> param,final Handler handler) {
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(goodsOfMerchantAPI, param);
                    String json = MyGet.doGet(url);
                    List<Goods> list = JSON.parseArray(json, Goods.class);
                    AppMethod.sendMessage(handler, list, Constants.CODE_REFRESH_GOODS_LIST);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 获得商品详情
     */
    public static void getGoodsDetails(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(getGoodsDetailsAPI, param);
                    //Log.d("======url-=======", url);
                    String json = MyGet.doGet(url);
                    Goods goods = JSON.parseObject(json, Goods.class);
                    if(goods!=null){
                        AppMethod.sendMessage(handler, goods, Constants.CODE_REFRASH_GOODS_DETAIL);
                    }
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 获得商品简单信息
     */
    public static void getGoodsSimpleInfo(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(getGoodsSimpleInfoAPI, param);
                    String json = MyGet.doGet(url);
                    Goods goods = JSON.parseObject(json, Goods.class);
                    if(goods!=null){
                        AppMethod.sendMessage(handler, goods, Constants.CODE_REFRASH_GOODS_DETAIL);
                    }
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 提交用户的发布
     */
    public static void commitPublish(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(publishCommitAPI, null);
                    String json = MyPost.doPost(url, param);
                    JSONObject jobj = JSON.parseObject(json);
                    Integer status = jobj.getInteger(RESULT_STATUS);
                    AppMethod.sendMessage(handler, status, Constants.CODE_REFRASH_PUBLISH_PAGE);
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 获取用户账户
     */
    public static void getUserAccount(final Map<String,Object> param,final Handler handler){
        new Thread() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(getUserAccountAPI, param);
                    String json = MyGet.doGet(url);
                    UserAccount ua = JSON.parseObject(json, UserAccount.class);
                    if(ua==null || ua.getUserId()==null){
                        JSONObject jobj = JSON.parseObject(json);
                        Integer status = jobj.getInteger(RESULT_STATUS);
                        AppMethod.sendMessage(handler, status, Constants.CODE_ORDER_USER_ACCOUNT);
                    }else{
                        AppMethod.sendMessage(handler, ua, Constants.CODE_ORDER_USER_ACCOUNT);
                    }
                } catch (Exception e) {
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        }.start();
    }
    /**
     * 提交购买订单
     */
    public static void commitBuyOrder(final Map<String,Object> param,final Handler handler){
        threadPools.execute(new Runnable() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(commitBuyOrderAPI, null);
                    String json = MyPost.doPost(url, param);
                    System.out.println(json);
                    if(!TextUtils.isEmpty(json)){
                        AppMethod.sendMessage(handler, json, Constants.CODE_COMMIT_ORDER);
                    }else{
                        AppMethod.sendMessage(handler, null,Constants.CODE_COMMIT_ORDER);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        });
    }
    /**
     * 根据支付宝返回的订单信息处理服务端订单
     */
    public static void notifyPayService(String result){
        final Map<String,Object> map = new HashMap<String,Object>();
        map.put("result", result);
        threadPools.execute(new Runnable() {
            public void run() {
                String url = null;
                try {
                    url = JsonResolveUtils.buildFullPath(notifyURL, null);
                    String json = MyPost.doPost(url,map);
                    JSONObject jobj = JSON.parseObject(json);
                    Integer status = jobj.getInteger(RESULT_STATUS);
                    if(status==0){//再次请求
                        MyPost.doPost(url,map);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 获取用户购买订单
     */
    public static void getUserOrders(final Map<String,Object> param,final Handler handler){
        threadPools.execute(new Runnable() {
            public void run() {
                try {
                    String url = JsonResolveUtils.buildFullPath(getUserOrdersAPI, param);
                    String json = MyGet.doGet(url);
                    Log.e("Yat3s", "getUserOrders---->"+json);
                    AppMethod.sendMessage(handler, json, Constants.CODE_GET_USER_ORDERS);
                } catch (Exception e) {
                    e.printStackTrace();
                    AppMethod.sendMessage(handler, null, Constants.CODE_NET_DATA_REQUEST_FAIL);
                }
            }
        });
    }
}
