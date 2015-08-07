package com.drivingevaluate.config;


public class Constants {
    /**
     * 调试模式
     */
    public static final boolean DEBUG = true;

    public static final String USER_INFO = "userInfo";
    /**
     * 定位当前位置返回request code值
     */
    public static final int LOCAL_REQUEST_CODE = 1;
    /**
     * 编辑修改返回request code值
     */
    public static final int EDIT_REQUEST_CODE = 2;

    /**
     * 编辑返回页面标题 key
     */
    public static final String TITLE = "title";
    /**
     * 清除当前位置返回 key
     */
    public static final String LOCAL_IF_CLEAR = "local_clear";

    /**
     * 新消息提醒方式
     */
    public static final String[] MESSAGE_REMIND = new String[] { "提醒 ", "智能 ", "屏蔽 "};

    /**
     * http 请求成功返回status值
     */
    public static final int HTTP_RESPONSE_OK = 1;
    /**
     * http 请求成功失败status值
     */
    public static final int HTTP_RESPONSE_FAIL = 0;

    /**
     * 连接超时时间 单位：ms
     */
    public static final int CONNECT_TIME_OUT = 25000;

    /**
     * 读取超时时间 单位：ms
     */
    public static final int READ_TIME_OUT = 25000;

    /**
     * 读取数据长度 单位：byte
     */
    public static final int READ_DATA_LENGTH = 1024;
    /**
     * 读取数据长度 单位：byte
     */
    public static final int SEND_MESSAGE_DELAY = 40;
    /**
     * 定位地址最大长度
     */
    public static final int ADDRESS_MAX_LENGTH = 50;
    /**
     * 发布标题最大长度
     */
    public static final int PUBLISH_TITLE_MAX_LENGTH = ADDRESS_MAX_LENGTH;
    /**
     * 发布内容最大长度
     */
    public static final int PUBLISH_CONTENT_MAX_LENGTH = 500;
    /**
     * 在上次的基础上获取新数据
     */
    public static final String GET_NEW = "timeOfGetNew";
    /**
     * 在上次的基础上获取更多
     */
    public static final String GET_MORE = "timeOfGetMore";
    /**
     * 商品id
     */
    public static final String GOODS_ID = "goodsId";
    /**
     * 发布id
     */
    public static final String PUBLISH_ID = "publishId";
    /**
     * pageSize
     */
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_NO = "pageNo";
    public static final String TIMESTAMP = "timestamp";
    public static final String LESS_THEN = "lessthen";
    public static final String USER_ID = "userId";
    public static final String FIRST_REPLY = "firstReply";

    public static final String IMG_PATH = "imgPath";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String CITY_CODE = "city_code";
    public static final String DISTRICT_CODE = "district_code";

    public static final String PUB_ADDRESS = "pubAddr";

    public static final String ADDRESS = "address";
    public static final String REAL_RName = "realName";
    public static final String ID_CARD_NO = "idCardNo";
    public static final String TEL_NO = "telNo";

    public static final String CONTENT = "content";
    public static final String TITLE_EXCEED_TAG = "标题不能超过";
    public static final String CONTENT_EXCEED_TAG = "标题不能超过";
    public static final String CONTENT_NULL_TAG = "内容不能为空";
    //缩略图前缀
    public static final String suoLuePreffix = "sl_";

    public static final String MERCHANT_ID = "merchantId";
    public static final String MERCHANT_NAME = "merchantName";


    /**
     * 昵称
     */
    public static final String NICK_NAME = "nickName";
    public static final String SEX = "sex";
    public static final String PERSONAL_SIGN = "sign";
    public static final String USER_STATUS = "status";
    /**
     * 文件
     */
    public static final String IMG_ID= "imgId";
    public static final String FILE  = "file";
    /**
     * 密码
     */
    public static final String OLD_PASSWD = "oldPwd";
    public static final String NEW_PASSWD = "newPwd";
    /**
     * blank
     */
    public static final String BLANK = "";
    public static final String RESULT = "result";

    /**-------------------操作状态码--------------------**/
    /**
     * 请求数据失败状态码
     */
    public static final int CODE_NET_DATA_REQUEST_FAIL = 0x10000;
    /**
     * 头像传递请求码(不能使用16位)
     */
    public static final int CODE_PASS_HEAD_DATA = 10001;

    /**-------------------API 状态码 --------------------**/
    /**
     * 用户注册状态成功码
     */
    public static final int CODE_USER_ADD_RESPONSE = 0x20000;
    /**
     * 验证码请求
     */
    public static final int CODE_IDENTIFY_CODE_REQUEST = 0x20001;
    /**
     * 刷新发布页面
     */
    public static final int CODE_REFRASH_PUBLISH_PAGE = 0x20002;
    /**
     * 发布说说成功
     */
    public static final int CODE_CIRCLE_PUBLISH_OK = 0x20003;
    /**
     * 刷新说说详情页面
     */
    public static final int CODE_REFRASH_PUBLIC_DETAIL = 0x20004;
    /**
     * 刷新评论
     */
    public static final int CODE_REFRASH_COMMENT_LIST = 0x20005;
    /**
     * 评论状态提示
     */
    public static final int CODE_SHOW_COMMENT_STATUS = 0x20006;
    /**
     * 刷新商品详情
     */
    public static final int CODE_REFRASH_GOODS_DETAIL = 0x20007;
    /**
     * 支付页面用户账户
     */
    public static final int CODE_ORDER_USER_ACCOUNT = 0x20008;
    /**
     * 定位请求
     */
    public static final int CODE_LOCATE_REQUEST = 0x20009;
    /**
     * 提交订单
     */
    public static final int CODE_COMMIT_ORDER = 0x20010;
    /**
     * 刷新说说列表
     */
    public static final int CODE_REFRASH_PUBLISH_LIST = 0x20011;
    /**
     * 刷新商户列表
     */
    public static final int CODE_REFRASH_MERCHANT_LIST = 0x20012;
    /**
     * 刷新登录
     */
    public static final int CODE_REFRESH_LOGIN = 0x20013;
    /**
     * 刷新商品列表
     */
    public static final int CODE_REFRESH_GOODS_LIST = 0x20014;
    /**
     * 刷新个人信息页面
     */
    public static final int CODE_REFRASH_MIME_INFO = 0x20015;
    /**
     * 保存用户信息状态
     */
    public static final int CODE_SAVE_USERINFO_STATUS = 0x20016;
    /**
     * 上传文件
     */
    public static final int CODE_UPLOAD_FILE = 0x20017;
    /**
     * 修改密码
     */
    public static final int CODE_MODIFY_USER_PASSWD = 0x20018;
    /**
     * 获得用户订单
     */
    public static final int CODE_GET_USER_ORDERS = 0x20019;
    /**
     * 商户详情
     */
    public static final int CODE_GET_MERCHANT_DETAIL = 0x20020;
    /**
     * 点赞
     */
    public static final int CODE_PARISE = 0x20021;
    /**
     * 评价
     */
    public static final int CODE_GOODS_JUDGE = 0x20022;
    /**
     * 获取评价
     */
    public static final int CODE_GET_GOODS_JUDGE = 0x20023;
}
