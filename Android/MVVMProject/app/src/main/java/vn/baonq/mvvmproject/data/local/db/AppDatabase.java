package vn.baonq.mvvmproject.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import vn.baonq.mvvmproject.data.local.db.dao.UserDao;
import vn.baonq.mvvmproject.data.model.db.User;

@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UserDao userDao();
}
