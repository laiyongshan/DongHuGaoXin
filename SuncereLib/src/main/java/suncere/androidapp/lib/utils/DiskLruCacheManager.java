package suncere.androidapp.lib.utils;

import android.content.Context;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import suncere.androidapp.lib.mvp.ui.MyApplication;


/**
 * Created by Hjo on 2017/4/6.
 */
public class DiskLruCacheManager {
    private static DiskLruCache mDiskLruCache=null;
    private DiskLruCache.Editor mEditor=null;
    private DiskLruCache.Snapshot mSnapshot=null;


    public DiskLruCacheManager(Context context, String uniqueName) {
        try {
               if (mDiskLruCache!=null) {  //不为null时 先关闭缓存
                   mDiskLruCache.close();
                   mDiskLruCache = null;
               }
            File cacheFile=getCacheFile(context,uniqueName);
            mDiskLruCache=DiskLruCache.open(cacheFile, MyApplication.getMyApplicationVersion(),1,50*1024*1024);//缓存文件的大小为50M
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getCacheFile(Context context, String uniqueName){
        String CachePath=null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || Environment.isExternalStorageRemovable()){//内存卡处于挂载和不可移除
            CachePath=context.getExternalCacheDir().getPath();//获取到的路径就是 /sdcard/Android/data/<application package>/cache
        }else{//获取到的路径是 /data/data/<application package>/cache
            CachePath=context.getCacheDir().getPath();
        }
        return new File(CachePath + File.separator + uniqueName);//File.separator 分隔符
    }

    /**
     * 获取Editor
     * @param key
     * @return
     */
    private DiskLruCache.Editor getEditor(String key){
        String md5key= MD5Utils.String2MD5(key);
        if (mDiskLruCache!=null){
            try {
                mEditor=mDiskLruCache.edit(md5key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  mEditor;
    }

    /**
     * 根据key获取Snapshot
     * @param key
     * @return
     */
    private DiskLruCache.Snapshot getSnapshot(String key){
        String md5key= MD5Utils.String2MD5(key);
        if (mDiskLruCache!=null){
            try {
                mSnapshot=mDiskLruCache.get(md5key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mSnapshot;
    }

    /**
     * 将字符串写入缓存文件
     * @param key
     * @param StringJson
     */
    public void writeString2CacheFile(String key, String StringJson){
        DiskLruCache.Editor editor=getEditor(key);
        BufferedWriter write=null;
        OutputStream out=null;
        OutputStreamWriter outWrite=null;
        try {
            out= editor.newOutputStream(0);
            outWrite=new OutputStreamWriter(out);
            write=new BufferedWriter(outWrite);
            write.write(StringJson);
            editor.commit();// 写完收工

        } catch (IOException e) {
            try {
                if (editor != null)
                    editor.abort();//放弃此次写入。
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
             try {//关闭
                 if (write!=null) write.close();
                 if (out!=null)out.close();
                 if (outWrite!=null)outWrite.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取缓存文件输出流
     * @param key
     * @return
     */
    private InputStream getInputStreamFromCacheFile(String key){
        DiskLruCache.Snapshot snapshot=getSnapshot(key);
        if (snapshot!=null) {
            InputStream intput = null;
            intput = snapshot.getInputStream(0);
            return intput;
        }
        return null;
    }

    /**
     * 将inputStream转为字符串
     * @param inputStream
     * @return
     */
    private  String InputStream2String(InputStream inputStream){
        StringBuilder   stringBuilder=new StringBuilder();
        BufferedReader reader=null;
        InputStreamReader inputStreamReader=null;
        try {
            inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
            reader=new BufferedReader(inputStreamReader);
            String lin;
            while ((lin=reader.readLine())!=null){
                stringBuilder.append(lin);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取缓存文件中的字符串
     * @param key
     * @return
     */
    public String getStringFromCacheFile(String key){
        return InputStream2String(getInputStreamFromCacheFile(key));
    }


    /**
     * 将object对象写入缓存
     * @param key
     * @param object
     */
    public void writeObject2CacheFile(String key , Object  object){
        DiskLruCache.Editor editor=getEditor(key);
        ObjectOutputStream oos=null;
        OutputStream os=null;

        try {
            os=editor.newOutputStream(0);
            oos=new ObjectOutputStream(os);
            oos.writeObject(object);
            oos.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (editor != null)
                    editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            try{
//                if (editor!=null)editor.abort();
                if (os!=null)os.close();
                if (oos!=null)oos.close();

            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    public <T> T getObjextFromCachFile(String key){
        T object=null;
        InputStream inputStream=getInputStreamFromCacheFile(key);
        if (inputStream==null)return null;
        ObjectInputStream ois=null;
        try {
            ois=new ObjectInputStream(inputStream);
            object= (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try{
                if (ois!=null)ois.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
        return  object;
    }




}
