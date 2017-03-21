package com.hibo.cms.advert.model;

import java.util.Date;

import com.hibo.bas.model.ModelBas;
import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年3月2日 下午3:05:20</p>
 * <p>类全名：com.hibo.cms.advert.model.AdvertBas</p>
 * 作者：吕康
 * 初审：
 * 复审：
 */
public class AdvertBas extends ModelBas{

	private static final long serialVersionUID = -4627142614337354786L;

	private Integer orders;

    private Date beginDate;

    private Date endDate;

    private String path;

    private String title;

    private Integer type;

    private String url;

    private String groupId;

    private String content;
    
    private String introduction;
    
    private String begin;
    
    private String end;
    
    private Boolean available;
    
    public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getBegin() {
		return DateUtil.dateToString(beginDate, "yyyy-MM-dd HH:mm:ss");
	}

	public void setBegin(String begin) {
		if(!StrUtil.isnull(begin)){
			this.beginDate = DateUtil.parseDate(begin, "yyyy-MM-dd HH:mm:ss");
			this.begin = begin;
		}
	}

	public String getEnd() {
		return DateUtil.dateToString(endDate, "yyyy-MM-dd HH:mm:ss");
	}

	public void setEnd(String end) {
		if(!StrUtil.isnull(end)){
			this.endDate = DateUtil.parseDate(end, "yyyy-MM-dd HH:mm:ss");
			this.end = end;
		}
	}

	public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
    
}