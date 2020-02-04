package kr.co.ldcc.assignment.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kr.co.ldcc.assignment.vo.BookmarkVo;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmark WHERE userId= :userId")
    List<BookmarkVo> getAll(String userId);
    //    LiveData<List<ReplyVo>> getAll();

    @Query("SELECT * FROM bookmark WHERE userId= :userId AND contentId= :contentId")
    BookmarkVo getOne(String userId, String contentId);

    @Insert
    void insert(BookmarkVo bookMarkVo);

    @Query("DELETE FROM bookmark WHERE userId= :userId AND contentId= :contentId")
    void delete(String userId, String contentId);

}
