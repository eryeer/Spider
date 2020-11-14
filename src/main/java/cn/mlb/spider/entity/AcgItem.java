package cn.mlb.spider.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname AcgItem
 * @Description TODO
 * @Date 2020/11/9 17:43
 * @Created by zhaochen
 */
public class AcgItem implements Serializable {

    private Integer id;
    private Date createTime;
    private Date updateTime;
    private String status;
    private String category;
    private String title;
    private String url;
    private Integer pageNumber;
    private String iconImageUrl;
    private String producer;
    private String series;
    private String level;
    private String name;
    private String version;
    private String materialType;
    private String toyClass;
    private String participator;
    private String releaseDate;
    private String rate;
    private String spec;
    private String price;
    private String personName;
    private String protoPerson;
    private String number;
    private String bannerImageUrl;



    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProtoPerson() {
        return protoPerson;
    }

    public void setProtoPerson(String protoPerson) {
        this.protoPerson = protoPerson;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public String getProducer() {
        return producer;
    }

    public String getSeries() {
        return series;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getToyClass() {
        return toyClass;
    }

    public String getParticipator() {
        return participator;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setIconImageUrl(String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public void setToyClass(String toyClass) {
        this.toyClass = toyClass;
    }

    public void setParticipator(String participator) {
        this.participator = participator;
    }

    @Override
    public String toString() {
        return "AcgItem{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pageNumber=" + pageNumber +
                ", iconImageUrl='" + iconImageUrl + '\'' +
                ", producer='" + producer + '\'' +
                ", series='" + series + '\'' +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", materialType='" + materialType + '\'' +
                ", toyClass='" + toyClass + '\'' +
                ", participator='" + participator + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rate='" + rate + '\'' +
                ", spec='" + spec + '\'' +
                ", price='" + price + '\'' +
                ", personName='" + personName + '\'' +
                ", protoPerson='" + protoPerson + '\'' +
                ", number='" + number + '\'' +
                ", bannerImageUrl='" + bannerImageUrl + '\'' +
                '}';
    }
}
