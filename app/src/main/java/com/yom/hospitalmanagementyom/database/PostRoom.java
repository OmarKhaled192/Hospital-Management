package com.yom.hospitalmanagementyom.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.yom.hospitalmanagementyom.model.Post;
import com.yom.hospitalmanagementyom.listeners.PostDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Post.class, version = 1)
public abstract class PostRoom extends RoomDatabase {
    public abstract PostDao postDao();

    private static volatile PostRoom INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final String DATABASE_NAME="Hospital Management" ;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PostRoom getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostRoom.class) {
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            PostRoom.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PostDao postDao = INSTANCE.postDao();

                Post post = new Post();
                post.setPost("JO");
                post.setId("0");
                post.setName("UO");

                postDao.insert(post);
            });
        }
    };
}
