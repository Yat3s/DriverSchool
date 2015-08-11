package com.drivingevaluate.config;
public class UrlConfig {
    public static String bathUrl="http://121.43.234.220:8090/JKDP/";
    public static String getMomentbyIdUrl="moment/getById.action";
    public static String addMomentUrl="moment/addMoment.action";
    public static String alterMomentUrl="moment/alterMoment.action";
    public static String deleteMomentUrl="moment/deleteMoment.action";
    public static String viewMomentUrl="moment/viewMoment.action";
    public static String likeMomentUrl="moment/likeMoment.action";
    public static String getMomentByAuthorUrl="moment/getByAuthor.action";
    public static final String getMomentByLocateUrl="/moment/getByLocate.action";
    public static String addDSchoolUrl="dsl/addDschool.action";
    public static String alterDSchoolUrl="dsl/alterDSchool.action";
    public static String deleteDSchoolUrl="dsl/deleteDSchool.action";
    public static String findDSchoolByIdUrl="dsl/findDSchoolById.action";
    public static String findSchoolByNameUrl="dsl/findSchoolByName.action";
    public static final String getDSchoolListBySortUrl="/dsl/getDSchoolListBySort.action";
    public static String nearByDSchoolUrl="dsl/nearByDSchool.action";
    public static String addCoachUrl="coach/addCoach.action";
    public static String deleteCoachUrl="coach/deleteCoach.action";
    public static String findCoachByIdUrl="coach/findCoachById.action";
    public static String alterCoachUrl="coach/alterCoach.action";
    public static String getCoachByDSchoolUrl="coach/getCoachByDSchool.action";
    public static String nearByMomentUrl="moment/nearByMoment.action";
    public static String getServerVer="version/getServerVer.action";
    public static String updateServerVer="version/updateServerVer.action";

    public static String addCommentUrl="comment/addComment.action";
    public static String deleteCommentUrl="comment/deleteComment.action";
    public static String getCommentbyIdUrl="comment/getById.action";
    public static String getCommentByAuthor="comment/getByAuthor.action";
    public static String getCommentByStyle="comment/getByStyle.action";



    // 用户资料文件夹
    private static final String PROFILE = "profile/";
    // 用户状态文件夹
    private static final String STATUS = "status/";
    //登录
    public static final String userLoginAPI = "/api/user/login.htm";
    //获取用户信息
    public static final String userInfoAPI = "/api/user/get_userinfo.htm";
    //获取商户列表
    public static final String merchantListAPI = "/api/merchant/get_list.htm";
    //获得商户详情
    public static final String merchantDetailAPI = "/api/merchant/get_detail.htm";
    //获取驾校全部教练的全部评论
    public static final String merchantCommentAPI = "/api/merchant/get_judges.htm";
    //获取发表列表
    private static final String publishListAPI = "/api/publish/get_list.htm";
    //获得商户的商品
    public static final String coachsOfMerchantAPI = "/api/goods/get_merchant_goods.htm";
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
    public static final String getGoodsDetailsAPI = "/api/goods/get_goods_details.htm";
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

}
