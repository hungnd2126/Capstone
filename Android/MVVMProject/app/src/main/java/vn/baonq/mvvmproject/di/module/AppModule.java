package vn.baonq.mvvmproject.di.module;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.baonq.mvvmproject.BuildConfig;
import vn.baonq.mvvmproject.R;
import vn.baonq.mvvmproject.data.AppDataManager;
import vn.baonq.mvvmproject.data.DataManager;
import vn.baonq.mvvmproject.data.local.db.AppDatabase;
import vn.baonq.mvvmproject.data.local.db.AppDbHelper;
import vn.baonq.mvvmproject.data.local.db.DbHelper;
import vn.baonq.mvvmproject.data.local.prefs.AppPreferencesHelper;
import vn.baonq.mvvmproject.data.local.prefs.PreferencesHelper;
import vn.baonq.mvvmproject.data.remote.ApiHeader;
import vn.baonq.mvvmproject.data.remote.ApiHelper;
import vn.baonq.mvvmproject.data.remote.AppApiHelper;
import vn.baonq.mvvmproject.di.ApiInfo;
import vn.baonq.mvvmproject.di.DbInfo;
import vn.baonq.mvvmproject.di.PreferenceInfo;
import vn.baonq.mvvmproject.utils.AppContansts;
import vn.baonq.mvvmproject.utils.rx.AppSchedulerProvider;
import vn.baonq.mvvmproject.utils.rx.SchedulerProvider;

@Module
public class AppModule {

    @Provides
    @ApiInfo
    String provideApiKey() {
        return "AIzaSyCKN4xQiXiTuUgYtHxPWEhQSy5CTFBn0bY";
    }


    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey) {
        return new ApiHeader.ProtectedApiHeader(apiKey);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DbInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DbInfo
    String provideDatabaseName() {
        return AppContansts.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppContansts.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
