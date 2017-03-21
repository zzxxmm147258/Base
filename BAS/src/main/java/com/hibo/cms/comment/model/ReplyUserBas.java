package com.hibo.cms.comment.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.model.ModelBas;

public class ReplyUserBas extends ModelBas{

	private static final long serialVersionUID = -914839779677998674L;

	private String mId;

    private String commentId;

    private String commentUserid;

    private String replyId;

    private String replyUserid;

    private String replyContent;

    private String reimgUrl;

    private String reimgSmallurl;

    private String reimgSmaother;

    private String type;

    private String operator;


    private String attr1="0";//回复消息默认未读

    private String attr2="1";//默认显示

    private String attr3;

    private String attr4;

    private String attr5;

    private List<MultipartFile> myPhoto;//辅助字段
    
    private List<String> myImg;//辅助字段
    /**
     * 用户名
     */
    private String username;
    /**
     *用户真实名称 
     */
    private String truename;
    /**
     * 用户头像地址
     */
    private String headpicture;
    /**
     * 用户昵称
     */
    private String nickname;
    
    public List<String> getMyImg() {
		return myImg;
	}

	public void setMyImg(List<String> myImg) {
		this.myImg = myImg;
	}

	public List<MultipartFile> getMyPhoto() {
		return myPhoto;
	}

	public void setMyPhoto(List<MultipartFile> myPhoto) {
		this.myPhoto = myPhoto;
	}

	public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getCommentUserid() {
        return commentUserid;
    }

    public void setCommentUserid(String commentUserid) {
        this.commentUserid = commentUserid == null ? null : commentUserid.trim();
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId == null ? null : replyId.trim();
    }

    public String getReplyUserid() {
        return replyUserid;
    }

    public void setReplyUserid(String replyUserid) {
        this.replyUserid = replyUserid == null ? null : replyUserid.trim();
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public String getReimgUrl() {
        return reimgUrl;
    }

    public void setReimgUrl(String reimgUrl) {
        this.reimgUrl = reimgUrl == null ? null : reimgUrl.trim();
    }

    public String getReimgSmallurl() {
        return reimgSmallurl;
    }

    public void setReimgSmallurl(String reimgSmallurl) {
        this.reimgSmallurl = reimgSmallurl == null ? null : reimgSmallurl.trim();
    }

    public String getReimgSmaother() {
        return reimgSmaother;
    }

    public void setReimgSmaother(String reimgSmaother) {
        this.reimgSmaother = reimgSmaother == null ? null : reimgSmaother.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4 == null ? null : attr4.trim();
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5 == null ? null : attr5.trim();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getHeadpicture() {
		return headpicture;
	}

	public void setHeadpicture(String headpicture) {
		this.headpicture = headpicture;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}