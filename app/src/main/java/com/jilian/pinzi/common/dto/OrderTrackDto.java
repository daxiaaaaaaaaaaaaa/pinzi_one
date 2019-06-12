package com.jilian.pinzi.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 订单跟踪 Dto
 */
public class OrderTrackDto implements Serializable {
    private static final long serialVersionUID = 6988552654472139927L;

    /**
     * deliveryMethodName : 中通快递
     * number : 73106065749767
     * quantity : 2
     * State : 3
     * deliveryMethodPhone : 6891521
     * logistics : [{"AcceptStation":"【深圳市】  快件已在 【深圳龙华】 签收, 签收人: 本人, 如有疑问请电联:18926456685 / 0755-29048383, 您的快递已经妥投, 如果您对我们的服务感到满意, 请给个五星好评, 鼓励一下我们【请在评价快递员处帮忙点亮五颗星星哦~】","AcceptTime":"2018-11-15 16:46:10"},{"AcceptStation":"【深圳市】  快件已到达 【深圳龙华】（0755-29048383）,业务员 潘洪密（18926456685） 正在第1次派件, 请保持电话畅通,并耐心等待","AcceptTime":"2018-11-15 08:58:41"},{"AcceptStation":"【深圳市】  快件离开 【深圳中心】 发往 【深圳龙华】","AcceptTime":"2018-11-15 03:58:00"},{"AcceptStation":"【深圳市】  快件到达 【深圳中心】","AcceptTime":"2018-11-15 03:36:02"},{"AcceptStation":"【广州市】  快件离开 【广州中心】 发往 【深圳中心】","AcceptTime":"2018-11-14 15:39:26"},{"AcceptStation":"【广州市】  快件到达 【广州中心】","AcceptTime":"2018-11-14 09:02:27"},{"AcceptStation":"【广州市】  快件离开 【市场部广州分部】 发往 【广州中心】","AcceptTime":"2018-11-13 17:10:26"},{"AcceptStation":"【广州市】  【市场部广州分部】（020-66805988） 的 美特斯邦威 （13143519993） 已揽收","AcceptTime":"2018-11-13 16:51:31"}]
     * deliveryMethodNumber : ZTO
     */

    private String deliveryMethodName;
    /** 快递单号 */
    private String number;
    /** 数量 */
    private int quantity;
    /** 物流状态: 0-无轨迹，1-已揽收，2-在途中，3-签收,4-问题件 */
    private String State;
    private String deliveryMethodPhone;
    private String deliveryMethodNumber;
    private String file;
    private String goodsImg;
    private List<LogisticsBean> logistics;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getDeliveryMethodPhone() {
        return deliveryMethodPhone;
    }

    public void setDeliveryMethodPhone(String deliveryMethodPhone) {
        this.deliveryMethodPhone = deliveryMethodPhone;
    }

    public String getDeliveryMethodNumber() {
        return deliveryMethodNumber;
    }

    public void setDeliveryMethodNumber(String deliveryMethodNumber) {
        this.deliveryMethodNumber = deliveryMethodNumber;
    }

    public List<LogisticsBean> getLogistics() {
        return logistics;
    }

    public void setLogistics(List<LogisticsBean> logistics) {
        this.logistics = logistics;
    }

    public static class LogisticsBean {
        /**
         * AcceptStation : 【深圳市】  快件已在 【深圳龙华】 签收, 签收人: 本人, 如有疑问请电联:18926456685 / 0755-29048383, 您的快递已经妥投, 如果您对我们的服务感到满意, 请给个五星好评, 鼓励一下我们【请在评价快递员处帮忙点亮五颗星星哦~】
         * AcceptTime : 2018-11-15 16:46:10
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
