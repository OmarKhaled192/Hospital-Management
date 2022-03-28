package com.yom.hospitalmanagementyom.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yom.hospitalmanagementyom.model.Post;

@Database(entities = Post.class, version = 1)
public abstract class PostRoom extends RoomDatabase {
    private static volatile PostRoom instance;
    public abstract PostDao postDao();
    private static final String DATABASE_NAME="Hospital Management" ;

    public static synchronized PostRoom getInstance(Context context){
        if(instance!=null){
            instance= Room.databaseBuilder(context.getApplicationContext(),PostRoom.class,
                    DATABASE_NAME).fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDataAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDataAsyncTask extends AsyncTask<Void,Void,Void>{

        private final PostDao postDao;
        public PopulateDataAsyncTask( PostRoom db){
            postDao= db.postDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Post post=new Post();
            post.setPost("JO");
            post.setId("0");
            post.setName("UO");

            postDao.insert(post);
            return null;
        }
    }
}
