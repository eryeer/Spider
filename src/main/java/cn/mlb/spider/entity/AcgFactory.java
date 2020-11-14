package cn.mlb.spider.entity;

import java.util.Date;

/**
 * @Classname AcgFactory
 * @Description TODO
 * @Date 2020/11/14 19:39
 * @Created by zhaochen
 */
public class AcgFactory {
    private Integer id;
    private Date createTime;
    private Date updateTime;
    private String status;
    private String title;
    private String url;
    private String introduce;
    private String type;
    private String creator;
    private String series;
    private String bannerImageUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    @Override
    public String toString() {
        return "AcgFactory{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", introduce='" + introduce + '\'' +
                ", type='" + type + '\'' +
                ", creator='" + creator + '\'' +
                ", series='" + series + '\'' +
                ", bannerImageUrl='" + bannerImageUrl + '\'' +
                '}';
    }
}
