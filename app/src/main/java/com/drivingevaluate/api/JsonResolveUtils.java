package com.drivingevaluate.api;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drivingevaluate.app.ServerConf;
import com.drivingevaluate.util.MyPost;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        sb.append(ServerConf.SERVER_IP).append(apiPath);
        if(params==null){
            return sb.toString();
        }
        sb.append(WENHAO);
        return generateParamentersString(sb,apiPath,params).toString();
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
                    Log.e("Yat3s","status:"+status);
                    if(status==0){//再次请求
                        MyPost.doPost(url,map);
                    }
                } catch (Exception e) {
                    Log.e("Yat3s","status:"+e.toString());
                }
            }
        });
    }


}
