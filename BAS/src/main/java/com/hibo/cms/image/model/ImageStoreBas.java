package com.hibo.cms.image.model;

import com.hibo.bas.model.ModelBas;

public class ImageStoreBas extends ModelBas{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3988191635050168865L;

	private String mId;

    private String sys;

    private String type;

    private String icon;

    private String imgUrl;

    private String imgBigurl;

    private String imgInurl;

    private String imgSmallurl;

    private String url;

    private String operator;

    private String dis;

    private String attr1;

    private String attr2;

    private String attr3;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys == null ? null : sys.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getImgBigurl() {
        return imgBigurl;
    }

    public void setImgBigurl(String imgBigurl) {
        this.imgBigurl = imgBigurl == null ? null : imgBigurl.trim();
    }

    public String getImgInurl() {
        return imgInurl;
    }

    public void setImgInurl(String imgInurl) {
        this.imgInurl = imgInurl == null ? null : imgInurl.trim();
    }

    public String getImgSmallurl() {
        return imgSmallurl;
    }

    public void setImgSmallurl(String imgSmallurl) {
        this.imgSmallurl = imgSmallurl == null ? null : imgSmallurl.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis == null ? null : dis.trim();
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
    }
}