package com.hibo.cms.comment.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hibo.bas.model.ModelBas;

public class CommentUserBas extends ModelBas{

	private static final long serialVersionUID = -4215425672250981770L;

	private String mId;

    private String commentUserid;

    private String commentContent;

    private Integer praiseNum;

    private Integer replyNum;

    private String coimgUrl;

    private String coimgSmallurl;

    private String coimgSmaother;

    private String type;

    private String operator;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;

    private List<MultipartFile> myPhoto;//辅助字段
    
    private List<String> myImg;//辅助字段
    
    private List<ReplyUserBas> replyUserBas;//辅助字段
    
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

	public List<ReplyUserBas> getReplyUserBas() {
		return replyUserBas;
	}

	public void setReplyUserBas(List<ReplyUserBas> replyUserBas) {
		this.replyUserBas = replyUserBas;
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

    public String getCommentUserid() {
        return commentUserid;
    }

    public void setCommentUserid(String commentUserid) {
        this.commentUserid = commentUserid == null ? null : commentUserid.trim();
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public String getCoimgUrl() {
        return coimgUrl;
    }

    public void setCoimgUrl(String coimgUrl) {
        this.coimgUrl = coimgUrl == null ? null : coimgUrl.trim();
    }

    public String getCoimgSmallurl() {
        return coimgSmallurl;
    }

    public void setCoimgSmallurl(String coimgSmallurl) {
        this.coimgSmallurl = coimgSmallurl == null ? null : coimgSmallurl.trim();
    }

    public String getCoimgSmaother() {
        return coimgSmaother;
    }

    public void setCoimgSmaother(String coimgSmaother) {
        this.coimgSmaother = coimgSmaother == null ? null : coimgSmaother.trim();
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
}