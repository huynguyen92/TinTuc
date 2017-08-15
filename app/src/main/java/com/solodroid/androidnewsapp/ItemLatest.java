package com.solodroid.androidnewsapp;

public class ItemLatest {

	private String NId;
	private String CatId;
	private String NewsHeading;
	//private String NewsDescription;
	private String NewsImage;
	private String NewsDate;
	private String NewSource;

	private String NId2;
	private String CatId2;
	private String NewsHeading2;
	//private String NewsDescription2;
	private String NewsImage2;
	private String NewsDate2;
	private String NewSource2;

    private String data_type;

    private String aid;
    private String ads_type;
    private String ads_unit_id;
    private String ads_heading;
    private String ads_image;
    private String target_url;
    private String ads_active;


	public ItemLatest(String id, String ads_type, String ads_unit_id, String ads_heading, String ads_image, String target_url, String ads_active){
		this.aid = id;
		this.ads_type = ads_type;
		this.ads_unit_id = ads_unit_id;
		this.ads_heading = ads_heading;
		this.ads_image = ads_image;
		this.target_url = target_url;
		this.ads_active = ads_active;
	}

	public ItemLatest() {

	}
/*
	getter and setter ItemLatest ads post
 */
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

	public String getNId() {
		return NId;
	}

	public void setNId(String NId) {
		this.NId = NId;
	}

	public String getCatId() {
		return CatId;
	}

	public void setCatId(String catId) {
		CatId = catId;
	}

	public String getNewsHeading() {
		return NewsHeading;
	}

	public void setNewsHeading(String newsHeading) {
		NewsHeading = newsHeading;
	}

	public String getNewsImage() {
		return NewsImage;
	}

	public void setNewsImage(String newsImage) {
		NewsImage = newsImage;
	}

	public String getNewsDate() {
		return NewsDate;
	}

	public void setNewsDate(String newsDate) {
		NewsDate = newsDate;
	}

	public String getNewSource() {
		return NewSource;
	}

	public void setNewSource(String newSource) {
		NewSource = newSource;
	}

	public String getNId2() {
		return NId2;
	}

	public void setNId2(String NId2) {
		this.NId2 = NId2;
	}

	public String getCatId2() {
		return CatId2;
	}

	public void setCatId2(String catId2) {
		CatId2 = catId2;
	}

	public String getNewsHeading2() {
		return NewsHeading2;
	}

	public void setNewsHeading2(String newsHeading2) {
		NewsHeading2 = newsHeading2;
	}

	public String getNewsImage2() {
		return NewsImage2;
	}

	public void setNewsImage2(String newsImage2) {
		NewsImage2 = newsImage2;
	}

	public String getNewsDate2() {
		return NewsDate2;
	}

	public void setNewsDate2(String newsDate2) {
		NewsDate2 = newsDate2;
	}

	public String getNewSource2() {
		return NewSource2;
	}

	public void setNewSource2(String newSource2) {
		NewSource2 = newSource2;
	}
}