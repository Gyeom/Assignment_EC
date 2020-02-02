package kr.co.ldcc.assignment.Vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reply")
public class ReplyVo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userId;
    public String replyData;
    public String contentId;

    public ReplyVo(String userId, String replyData, String contentId) {
        this.userId = userId;
        this.replyData = replyData;
        this.contentId = contentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyData() {
        return replyData;
    }

    public void setReplyData(String replyData) {
        this.replyData = replyData;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "ReplyVo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", replyData='" + replyData + '\'' +
                ", contentId=" + contentId +
                '}';
    }
}
