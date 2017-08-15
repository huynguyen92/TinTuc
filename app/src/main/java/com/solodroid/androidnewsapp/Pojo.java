package com.solodroid.androidnewsapp;

public class Pojo {
	
	private int id;
	private String nId;
	private String CId;
	private String CategoryName;
 	private String NewsHeading;
	private String NewsImage;
	private String NewsDesc;

	private String SId;
	private String NewSource;

	public Pojo()
	{
	}
	
	public Pojo(String newId)
	{
		this.nId=newId;
	}
	
	public Pojo(String newid,String cid,String categoryname,String newsheading,String newsimage,String newsdesc,String newsdate, String sid, String sourceName)
	{
		this.nId=newid;
		this.CId=cid;
		this.CategoryName=categoryname;
 		this.NewsHeading=newsheading;
		this.NewsImage=newsimage;
		this.NewsDesc=newsdesc;

		this.SId=sid;
		this.NewSource=sourceName;

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getNewsDesc() {
		return NewsDesc;
	}

	public void setNewsDesc(String newsdesc) {
		this.NewsDesc = newsdesc;
	}
	

	public String getnId() {
		return nId;
	}

	public void setnId(String nId) {
		this.nId = nId;
	}

	public String getSId() {
		return SId;
	}

	public void setSId(String SId) {
		this.SId = SId;
	}

	public String getNewSource() {
		return NewSource;
	}

	public void setNewSource(String newSource) {
		NewSource = newSource;
	}
}
