package io.github.huafoog.fir.common.core.constant;

public class ProductConstants {


    public enum  AttrEnum{
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");
        private int code;
        private String msg;

        AttrEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 上下架状态枚举
     */
    public enum PublishStatusEnum{
        CREATE(0,"新建"),UP(1,"上架"),DOWN(2,"下架");
        private int code;
        private String msg;

        PublishStatusEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
