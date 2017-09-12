package cn.mlb.spider.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Media implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String url;
	private String img;// img的网络url
	private String imgLoc;// img的本地相对地址
	private Double fileSize;// MB
	private String category;
	private Time duration;
	private Date createTime;
	private Date updateTime;
	private Double rating;

	public String getImgLoc() {
		return imgLoc;
	}

	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Time getDuration() {
		return duration;
	}

	public void setDuration(Time duration) {
		this.duration = duration;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", title=" + title + ", url=" + url
				+ ", img=" + img + ", imgLoc=" + imgLoc + ", fileSize="
				+ fileSize + ", category=" + category + ", duration="
				+ duration + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", rating=" + rating + "]";
	}

}
