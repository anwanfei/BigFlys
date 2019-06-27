package com.anfly.bigfly.distance.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Anfly
 * @date 2019/5/5
 * description：
 */
@Entity
public class NewsDbBean {
    @Id(autoincrement = true)
    private Long id;
    private String thumbnailUrl;
    private String title;
    private String authorName;
    private String date;
    private String category;
    @Generated(hash = 989898295)
    public NewsDbBean(Long id, String thumbnailUrl, String title, String authorName,
            String date, String category) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.authorName = authorName;
        this.date = date;
        this.category = category;
    }
    @Generated(hash = 2049854230)
    public NewsDbBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthorName() {
        return this.authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
