package kr.co.ldcc.assignment.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kr.co.ldcc.assignment.Vo.BmarkVo;
import kr.co.ldcc.assignment.Vo.ReplyVo;

@Dao
public interface BmarkDao {
    @Query("SELECT * FROM bmark WHERE userId= :userId")
    List<BmarkVo> getAll(String userId);
    //    LiveData<List<ReplyVo>> getAll();

    @Query("SELECT * FROM bmark WHERE userId= :userId AND contentId= :contentId")
    BmarkVo getOne(String userId, String contentId);

    @Insert
    void insert(BmarkVo bmarkVo);

    @Query("DELETE FROM bmark WHERE userId= :userId AND contentId= :contentId")
    void delete(String userId, String contentId);

}
