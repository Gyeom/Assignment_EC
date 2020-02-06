package kr.co.ldcc.assignment.vo;

import java.util.ArrayList;
import java.util.List;

public class Video {
    VideoMeta meta;
    ArrayList<VideoVo> documents;

    public Video(VideoMeta meta, ArrayList<VideoVo> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public VideoMeta getMeta() {
        return meta;
    }

    public void setMeta(VideoMeta meta) {
        this.meta = meta;
    }

    public ArrayList<VideoVo> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<VideoVo> documents) {
        this.documents = documents;
    }
}
