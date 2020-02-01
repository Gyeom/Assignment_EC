package kr.co.ldcc.assignment.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import kr.co.ldcc.assignment.Vo.ReplyVo;

@Dao
public interface ReplyDao {
    @Query("SELECT * FROM reply WHERE contentId= :contentId")
    List<ReplyVo> getAll(String contentId);
    //    LiveData<List<ReplyVo>> getAll();
    @Insert
    void insert(ReplyVo replyVo);

    @Query("DELETE FROM reply")
    void deleteAll();

}
