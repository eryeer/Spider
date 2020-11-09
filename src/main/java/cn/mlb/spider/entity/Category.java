package cn.mlb.spider.entity;


/**
 * @Classname Category
 * @Description TODO
 * @Date 2020/11/9 17:54
 * @Created by zhaochen
 */
public class Category {

    private String name;
    private int pageCount;
    private String subUrl;

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public Category(String name, int pageCount, String subUrl){
        this.name = name;
        this.pageCount = pageCount;
        this.subUrl = subUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", pageCount=" + pageCount +
                ", subUrl='" + subUrl + '\'' +
                '}';
    }
}
