package com.solodroid.androidnewsapp;

public class ItemNewsByCategory {
	
	private String CId;
 	private String CategoryName;
	private String CategoryImage;
	private String CatId;
	private String NewsHeading;
	private String NewsImage;
	private String NewsDate;
	private String NewSource;

	private String data_type;

	private String aid;
	private String ads_type;
	private String ads_unit_id;
	private String ads_heading;
	private String ads_image;
	private String target_url;
	private String ads_active;

	public ItemNewsByCategory(String id, String ads_type, String ads_unit_id, String ads_heading, String ads_image, String target_url, String ads_active){
		this.aid = id;
		this.ads_type = ads_type;
		this.ads_unit_id = ads_unit_id;
		this.ads_heading = ads_heading;
		this.ads_image = ads_image;
		this.target_url = target_url;
		this.ads_active = ads_active;
	}

	public ItemNewsByCategory(){}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAds_type() {
		return ads_type;
	}

	public void setAds_type(String ads_type) {
		this.ads_type = ads_type;
	}

	public String getAds_unit_id() {
		return ads_unit_id;
	}

	public void setAds_unit_id(String ads_unit_id) {
		this.ads_unit_id = ads_unit_id;
	}

	public String getAds_heading() {
		return ads_heading;
	}

	public void setAds_heading(String ads_heading) {
		this.ads_heading = ads_heading;
	}

	public String getAds_image() {
		return ads_image;
	}

	public void setAds_image(String ads_image) {
		this.ads_image = ads_image;
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}

	public String getAds_active() {
		return ads_active;
	}

	public void setAds_active(String ads_active) {
		this.ads_active = ads_active;
	}

	public String getNewSource() {
		return NewSource;
	}

	public void setNewSource(String newSource) {
		NewSource = newSource;
	}

	public String getCId() {
		return CId;
	}

	public void setCId(String cid) {
		this.CId = cid;
	}
 	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	
	public String getCategoryImage() {
		return CategoryImage;
	}

	public void setCategoryImage(String categoryimage) {
		this.CategoryImage = categoryimage;
	}
	
	
	public String getCatId() {
		return CatId;
	}

	public void setCatId(String catid) {
		this.CatId = catid;
	}
	
 	public String getNewsHeading() {
		return NewsHeading;
	}

	public void setNewsHeading(String newsheading) {
		this.NewsHeading = newsheading;
	}

	public String getNewsImage() {
		return NewsImage;
	}

	public void setNewsImage(String newsimage) {
		this.NewsImage = newsimage;
	}
	public String getNewsDate() {
		return NewsDate;
	}

	public void setNewsDate(String newsdate) {
		this.NewsDate = newsdate;
	}

}
