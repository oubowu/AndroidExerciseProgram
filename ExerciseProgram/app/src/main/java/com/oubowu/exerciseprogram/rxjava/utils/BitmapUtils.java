package com.oubowu.exerciseprogram.rxjava.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.oubowu.exerciseprogram.utils.ImageLoader.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * 类名： BitmapUtils
 * 作者: oubowu
 * 时间： 2016/1/14 16:21
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class BitmapUtils {

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static DiskLruCache mDiskLruCache;

    private static long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        final boolean available = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath = available ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public static void storeBitmap(Context context, Bitmap bitmap, String name) {
        chkDiskLruCache(context);

    }

    private static void chkDiskLruCache(Context context) {
        if (mDiskLruCache == null) {
            File diskCacheDir = getDiskCacheDir(context, "bitmap");
            if (!diskCacheDir.exists()) {
                diskCacheDir.mkdirs();
            }
            if (mDiskLruCache == null && getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
                try {
                    mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
