package kr.co.ldcc.assignment.Vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reply")
public class ReplyVo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String writer;
    public String replyData;
    public String contentId;

    public ReplyVo(String writer, String replyData, String contentId) {
        this.writer = writer;
        this.replyData = replyData;
        this.contentId = contentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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
                ", writer='" + writer + '\'' +
                ", replyData='" + replyData + '\'' +
                ", contentId=" + contentId +
                '}';
    }
}
