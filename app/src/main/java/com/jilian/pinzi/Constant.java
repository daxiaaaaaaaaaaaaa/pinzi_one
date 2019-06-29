package com.jilian.pinzi;

/**
 * 全局常量
 *
 * @author weishixiong
 * @Time 2018-03-16
 */

public class Constant {

    public final static String PARAM = "param";

    public final static String APP_ID = "wxc0869ae09394b840";

    /**
     * 请求后台的所有API接口都在这里配置;统一管理
     */
    public static class Server {
        public final static int SUCCESS_CODE = 200;
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


        public static String FIRE_URL = "http://39.108.14.94:9088";


        public final static String SEVEN_URL = "http://ptk6jx7j2.bkt.clouddn.com";

    }


    /**
     * sp 常量
     */
    public static class SP_VALUE {

        public static final String SP = "sp";//sp
            public static final String CONSTANT = "constant";//
        public static final String ISCOMIN = "is_comin";//是否进入过APP
        public static final String LOGIN_DTO = "LOGIN_DTO";//登录实体key
        public static final String SESSION_ID = "session_id";//

        public static final String HISTORY_SP = "history_sp";//sp
        public static final String HISTORY = "history";//

        public static final String INVOICE = "invoice";//发票信息
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
