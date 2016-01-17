package com.oubowu.exerciseprogram.rxjava.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.oubowu.exerciseprogram.utils.DiskLruCache;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类名： DiskCacheUtils
 * 作者: oubowu
 * 时间： 2016/1/14 17:14
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public class DiskCacheUtils {

    // 1mb
    private static final int MB = 1024 * 1024;

    // 缓存路径
    private static final String IMAGE_DISK_CACHE = "bitmap";

    private DiskLruCache mDiskLruCache;

    // 实例
    private static volatile DiskCacheUtils mDiskCahe;

    private DiskCacheUtils(Context context) {
        initDiskCache(context);
    }

    /**
     * 双重检查锁定
     *
     * @param context
     * @return
     */
    public static DiskCacheUtils getInstance(Context context) {
        if (mDiskCahe == null) {
            synchronized (DiskCacheUtils.class) {
                if (mDiskCahe == null) {
                    mDiskCahe = new DiskCacheUtils(context);
                }
            }
        }
        return mDiskCahe;
    }

    // 初始化sd卡缓存
    private void initDiskCache(Context context) {
        File cacheDir = getDiskCacheDir(context, IMAGE_DISK_CACHE);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        try {
            if (getUsableSpace(cacheDir) > 50 * MB)
                mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取sd卡缓存的目录，若挂载了sd卡就使用sd卡缓存，否则使用应用的缓存目录
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public static long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs statFs = new StatFs(path.getPath());
        return statFs.getBlockSize() * statFs.getAvailableBlocks();
    }

    /**
     * 获取保存的图片
     */
    public Bitmap drawableToBitmap(String name) {
        try {
            final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKeyFormUrl(name));
            final InputStream is = snapshot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            closeQuietly(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储图片
     */
    public void storeBitmap(Context context, Bitmap bitmap, String name) {
        try {
            // 每当我们调用一次DiskLruCache的edit()方法时，都会向journal文件中写入一条DIRTY记录，表示我们正准备写入一条缓存数据，
            // 但不知结果如何。然后调用commit()方法表示写入缓存成功，这时会向journal中写入一条CLEAN记录，
            // 意味着这条“脏”数据被“洗干净了”，调用abort()方法表示写入缓存失败，这时会向journal中写入一条REMOVE记录。
            final DiskLruCache.Editor editor = mDiskLruCache.edit(hashKeyFormUrl(name));
            final DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKeyFormUrl(name));
            if (editor != null && snapshot == null) {
//                KLog.e("储存图片: " + name);
                OutputStream os = editor.newOutputStream(0);
                if (writeBitmapToDisk(bitmap, os)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                closeQuietly(os);
            } else if (snapshot != null) {
                snapshot.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将Bitmap写入DiskLruCache
    private boolean writeBitmapToDisk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8 * 1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        boolean res = true;
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        } finally {
            closeQuietly(bos);
        }
        return res;
    }

    public void remove(String name) {
        try {
            mDiskLruCache.remove(hashKeyFormUrl(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }

        } catch (IOException var1) {
        }
    }

}
