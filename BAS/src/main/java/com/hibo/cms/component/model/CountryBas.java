package com.hibo.cms.component.model;

import java.io.Serializable;

import com.hibo.bas.util.ObjectId;

public class CountryBas implements Serializable{
	
	private static final long serialVersionUID = 941169337350943119L;
	
	private String id = ObjectId.getUUId();

    private String title;

    private String code;

    private Boolean locked;

    private String country1;

    private String country2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1 == null ? null : country1.trim();
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2 == null ? null : country2.trim();
    }
}