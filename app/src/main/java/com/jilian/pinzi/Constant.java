package com.jilian.pinzi;

import com.jilian.pinzi.ui.PackageUtils;

/**
 * 全局常量
 *
 * @author weishixiong
 * @Time 2018-03-16
 */

public class Constant {

    public final static String PARAM = "param";

    public final static String APP_ID = "wxc0869ae09394b840";

    public final static String WXAPP_SECRET = "3f507365ff972104eef2d35a6b425901";


    /**
     * 请求后台的所有API接口都在这里配置;统一管理
     */
    public static class Server {
        public final static int SUCCESS_CODE = 200;
        public final static int BIND_CODE = 220;//已经注册 需要绑定手机号码
        public final static int REGISTER_CODE = 210;//未注册
        public final static int NOPERFORM_CODE = 202;//未完善资料
        public final static int CHECKING_CODE = 204;//用户审核中
        public final static int CHECKFAILUER_CODE = 206;// 用户未通过审核;
        //服务器超时时间 16 秒
        public final static int TIME_OUT = 16;


        //一期
        //public static String BASE_URL = "http://39.108.14.94:9006/donghui_app/";

        //二期
        public static String BASE_URL = "http://39.108.14.94:9010/donghui_app_test/";
        //微信
        public static String WX_URL = " https://api.weixin.qq.com/sns/oauth2/";

        //二维码
        public static String FIRE_URL = "http://39.108.14.94:9082";

        // 7 牛 视频
        public final static String SEVEN_URL = "http://qiniu.dhygvip.com";

        //下载更新
        public static String DOWN_URL = "http://39.108.14.94:9007/";



       // 消费排行-用户消费排行榜：
        public static final String url_01 = "http://www.dhygvip.com/#/user-consumption-ranking";
       // 消费排行-单商品销量排行榜：
       public static final  String url_02 = "http://www.dhygvip.com/#/single-product-ales-list";
        //消费排行-区域消费排行榜：
        public static final  String url_03 = "http://www.dhygvip.com/#/regional-consumption-ranking";

        //订单统计-区域统计：
        public static final  String url_04 =  "http://www.dhygvip.com/#/regional-statistics";
        //订单统计-店铺统计：
        public static final  String url_05 = "http://www.dhygvip.com/#/store-statistics";
        //订单统计-支付方式统计：
        public static final  String url_06 =  "http://www.dhygvip.com/#/payment-statistics";
       // 订单统计-订单状态统计：
       public static final  String url_07= "http://www.dhygvip.com/#/order-status-statistics";

        //营收统计：
        public static final  String url_08 = "http://www.dhygvip.com/#/revenue-statistics2";

       // 充值统计：
        //提现统计：
       public static final  String url_09 =  "http://www.dhygvip.com/#/cash-withdrawal-statistics";


        //分享统计：
        public static final  String url_10 =  "http://www.dhygvip.com/#/sharing-statistics2";


        //销量统计-平台销量统计：
        public static final  String url_11 = "http://www.dhygvip.com/#/platform-statistics";
        //销量统计-城市销量统计：
        public static final  String url_12 = "http://www.dhygvip.com/#/regional-statistics";
        //销量统计-城市销量走势统计：
        public static final  String url_13 = "http://www.dhygvip.com/#/city-sales-trend-statistics";
        //销量统计-店铺销量统计：
        public static final  String url_14 = "http://www.dhygvip.com/#/store-statistics";
        //销量统计-店铺销量走势统计:
        public static final  String url_15 = "http://www.dhygvip.com/#/store-sales-trend-statistics";

        //单品统计:
        public static final  String url_16 = "http://www.dhygvip.com/#/single-item-statistics";
        //用户购买统计：
        public static final  String url_17 = "http://www.dhygvip.com/#/user-purchase-statistics2";


    }


    /**
     * sp 常量
     */
    public static class SP_VALUE {

        public static final String SP = "sp";//sp
        public static final String CONSTANT = "constant";//
        public static final String ISCOMIN = "is_comin";//是否进入过APP
        public  final static  String LOGIN_DTO =
                "LOGIN_DTO"+ PackageUtils.getVersionName(PinziApplication.getInstance());//登录实体key
        public static final String SESSION_ID = "session_id";//

        public static final String HISTORY_SP = "history_sp";//sp
        public static final String HISTORY = "history";//

        public static final String INVOICE = "invoice";//发票信息

        public static final String INSTALL_SP = "install_sp";//sp
    }

    /**
     * 全局常量
     */
    public static class FINALVALUE {
        public static final String CLIENT_TRUST_PASSWORD = "证书的密码";
        public static final String CLIENT_AGREEMENT = "TLS";//使用协议
        public static final String CLIENT_TRUST_KEYSTORE = "pkcs12";//bks pkcs12
        //证书
        public static final int CERTIFICATE = 0;//R.raw.formal_environment (把证书文件放到 raw 目录下)


        public static final String FILE_PROVIDER = "com.jilian.pinzi.fileprovider";



    }
}
