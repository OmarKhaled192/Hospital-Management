package com.yom.hospitalmanagementyom.listeners;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Query("SELECT * FROM post_table")
    LiveData<List<Post>> getAllPostFromRoom();

    @Query("DELETE FROM post_table WHERE id=:id")
    void deletePostFromRoom(String id);
}
