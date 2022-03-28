package com.yom.hospitalmanagementyom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yom.hospitalmanagementyom.model.Post;
import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Update
    void updateR(Post post);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM Post")
    void deletePostFromRoom();

    @Query("SELECT * FROM Post")
    LiveData<List<Post>> getAllPostFromRoom();
}
