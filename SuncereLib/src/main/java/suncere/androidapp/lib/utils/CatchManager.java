package suncere.androidapp.lib.utils;

import android.util.Log;

import suncere.androidapp.lib.mvp.ui.MyApplication;


/**
 * Created by Hjo on 2017/5/12.
 */

public class CatchManager {


    /**
     * 获取缓存数据
     * @param key
     */
    public  static Object getDataFromCatch(String key){
        Log.d("CatchManagerTAG","缓存数据，key="+key);
        DiskLruCacheManager diskLruCacheManager = new DiskLruCacheManager(MyApplication.getMyApplicationContext(), key);
        Object model = diskLruCacheManager.getObjextFromCachFile(key);
        return model;

    }


    /**
     * 将数据写入缓存中
     * @param key
     * @param model
     */
    public  static void  putData2Cache(String key,Object model){
        Log.d("CatchManagerTAG","写入缓存数据，key="+key);
        DiskLruCacheManager diskLruCacheManager=new DiskLruCacheManager(MyApplication.getMyApplicationContext(),key);
        diskLruCacheManager.writeObject2CacheFile(key,model);
    }
}
