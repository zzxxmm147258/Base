package com.hibo.cms.advert.model;

import java.util.Date;

import com.hibo.bas.model.ModelBas;
import com.hibo.bas.util.DateUtil;
import com.hibo.bas.util.StrUtil;

public class AdvertGroupBas extends ModelBas{

	private static final long serialVersionUID = 2901415916622873362L;

	private String adPosition;

    private String name;

    private Date beginDate;

    private Date endDate;

    private Boolean available;

    private String remark;
    
    private String begin;
    
    private String end;
    
    private String adPositionName;
    
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
    
    public String getAdPositionName() {
		return adPositionName;
	}

	public void setAdPositionName(String adPositionName) {
		this.adPositionName = adPositionName;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(String adPosition) {
		this.adPosition = adPosition;
	}
    
}