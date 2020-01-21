package kr.co.ldcc.assignment.Vo;

public class VideoVo {
    String title;
    int play_time;
    String thumbnail;
    String url;
    String datetime;
    String author;

//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
//    Date curDate = null;
//    Date objDate = null;
//    @Override
//    public int compareTo(VideoVo obj) {
//        try{
//            curDate = simpleDateFormat.parse(this.datetime);
//            objDate = simpleDateFormat.parse(obj.datetime);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return curDate.compareTo(objDate);
//    }

    public VideoVo(String title, int play_time, String thumbnail, String url, String datetime, String author) {
        this.title = title;
        this.play_time = play_time;
        this.thumbnail = thumbnail;
        this.url = url;
        this.datetime = datetime;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlay_time() {
        return play_time;
    }

    public void setPlay_time(int play_time) {
        this.play_time = play_time;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "VideoVo{" +
                "title='" + title + '\'' +
                ", play_time=" + play_time +
                ", thumbnail='" + thumbnail + '\'' +
                ", url='" + url + '\'' +
                ", datetime='" + datetime + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
