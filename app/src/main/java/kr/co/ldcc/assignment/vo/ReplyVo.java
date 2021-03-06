package kr.co.ldcc.assignment.vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reply")
public class ReplyVo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String replyData;
    private String contentId;

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
