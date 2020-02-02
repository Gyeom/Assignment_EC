package kr.co.ldcc.assignment.Vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bmark")
public class BmarkVo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String thumbnail;
    public String url;
    public String datetime;
    public String userId;
    public String contentId;

    public BmarkVo(String title, String thumbnail, String url, String datetime, String userId, String contentId) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
        this.datetime = datetime;
        this.userId = userId;
        this.contentId = contentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "BmarkVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", url='" + url + '\'' +
                ", datetime='" + datetime + '\'' +
                ", userId='" + userId + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }
}
