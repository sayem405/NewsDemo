package com.jokerslab.newsdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

/**
 * Created by Sayem on 6/30/2015.
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static boolean deleteFile(String path) {
        boolean isDeleted = false;
        File file;
        try {
            file = new File(path);
            isDeleted = file.delete();
            Log.d("FilleUtil", "Deleted");
        } catch (Exception ex) {
            Log.d("FileUtils", "Delete issue. ex : " + ex.getMessage());
        }
        return isDeleted;
    }

    public static boolean deleteFolder(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteFolder(files[i]);
                    } else {
                        files[i].delete();
                        //Log.d(TAG,"deleted @" +files[i]);
                    }
                }
            }
        }
        return (directory.delete());
    }

    public static String getBase64EncodedString(String path) {
        if (path == null)
            return null;

        String msgData = null;
        File audioFile = new File(path);

        try {
            FileInputStream in = new FileInputStream(audioFile);
            byte[] bytes = new byte[(int) audioFile.length()];
            in.read(bytes);
            in.close();
            msgData = Base64.encodeToString(bytes, Base64.NO_WRAP);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        return msgData;
    }

    public static String getDataFromFile(File path) {
        if (path == null || !path.exists())
            return null;

        String msgData = null;

        try {
            FileInputStream in = new FileInputStream(path);
            byte[] bytes = new byte[(int) path.length()];
            in.read(bytes);
            in.close();
            msgData = String.valueOf(bytes);

        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        return msgData;
    }

    public static boolean saveFileForUrl(File file, String url_str) {
        OutputStream output = null;
        InputStream input = null;
        try {
            URL url = new URL(url_str);
            input = url.openStream();
            output = new FileOutputStream(file.getAbsolutePath());
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, bytesRead);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getImageFileFromBase64String(Context context, String base64ImageData) {
        FileOutputStream fos = null;
        long name;
        File file = null;
        try {
            name = new Date().getTime();
            File sdCard = FileUtils.getApplicationDirectory(context, Environment.DIRECTORY_PICTURES);
//            File dir = new File(sdCard.getAbsolutePath() + File.separator + MedicalDocumentsConstants.DOC_DIR);
//            dir.mkdirs();

            file = new File(sdCard, Long.toString(name) + MedicalDocumentsConstants.PICTURE_FILE_EXT_JPEG);
            file.createNewFile();

            if (base64ImageData != null) {
                fos = new FileOutputStream(file);//context.openFileOutput("imageName.png", Context.MODE_PRIVATE);
                byte[] decodedString = Base64.decode(base64ImageData, Base64.NO_WRAP);
                fos.write(decodedString);

                fos.flush();
                fos.close();
            }

        } catch (Exception e) {
            Log.d("file", e.getMessage());
        }

        return file;
    }

    public static File getAudioFileFromBase64String(Context context, String base64ImageData) {
        FileOutputStream fos = null;
        long name;
        File file = null;
        try {
            name = new Date().getTime();
            File sdCard = FileUtils.getApplicationDirectory(context, Environment.DIRECTORY_MUSIC);
//            File dir = new File(sdCard.getAbsolutePath() + File.separator + MedicalDocumentsConstants.DOC_DIR);
//            dir.mkdirs();

            file = new File(sdCard, Long.toString(name) + MedicalDocumentsConstants.AUDIO_RECORDER_FILE_EXT_3GP);
            file.createNewFile();

            if (base64ImageData != null) {
                fos = new FileOutputStream(file);//context.openFileOutput("imageName.png", Context.MODE_PRIVATE);
                byte[] decodedString = Base64.decode(base64ImageData, Base64.NO_WRAP);
                fos.write(decodedString);

                fos.flush();
                fos.close();
            }

        } catch (Exception e) {
            Log.d("file", e.getMessage());

        }

        return file;
    }

    public static File getApplicationDirectory(Context context, String directoryType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return Environment.getExternalStoragePublicDirectory(directoryType);
        }
        return context.getExternalFilesDir(directoryType);
    }

    /**
     * @param context
     * @param directoryType like {@link Environment#DIRECTORY_MUSIC}
     * @return temp directory, if no temp directory return null
     */
    public static File getTemporaryDirectory(Context context, String directoryType) {
        File directoryFile = getApplicationDirectory(context, directoryType);
        directoryFile = new File(directoryFile, Constants.TEMP_DIRECTORY);
        if (directoryFile.exists()) return directoryFile;
        else return null;
    }

    public static boolean deleteFile(File file) {
        if (file == null) return false;

        if (file.exists()) {
            if (!file.delete()) {
                Log.d(TAG, "UtilCommon @" + " file can not be deleted");
            } else {
                return true;
            }
        } else {
            Log.d(TAG, "UtilCommon @" + " file not found");
        }

        return false;
    }

    public static String copyDatabaseToSdCard(Context mContext) {
        try {
            String DATABASE_NAME = Constants.DATABASE;
            final String DB_PATH;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
            } else {
                DB_PATH = "/data/data/" + mContext.getPackageName() + "/databases/" + DATABASE_NAME;
            }
            File f1 = new File(DB_PATH);
            if (f1.exists()) {
                File f2 = new File(FileUtils.getApplicationDirectory(mContext, Environment.DIRECTORY_PICTURES), DATABASE_NAME);
                f2.createNewFile();
                InputStream in = new FileInputStream(f1);
                OutputStream out = new FileOutputStream(f2);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.d(TAG, "Database File: " + f2.getAbsolutePath());
                return DB_PATH;
            }
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.toString());
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return null;
    }

    public static File getStatusImageFilePath(Context context) {
        File extFileDir;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            extFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else
            extFileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(extFileDir, "Status");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getExternalDirectory(Context context, String directoryType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return Environment.getExternalStoragePublicDirectory(directoryType);
        } else
            return context.getExternalFilesDir(directoryType);
    }

    public static boolean saveBase64ImageToFile(File file, String base64) throws IOException {
        if (Util.isEmpty(base64)) throw new NullPointerException("base 64 found null");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);

            fos.write(bytes);

            return true;
        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException | NullPointerException e) {
            Log.d(TAG, e.toString());
            return false;
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, e);
                }
        }
    }

    public static boolean saveByteArrayToFile(File file, byte[] bytes) throws IOException {
        if (bytes == null) throw new NullPointerException("found null");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);

            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w(TAG, e);
                }
        }
    }

    public static boolean saveBase64IntoFile(String base64, File file) {
        byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);
        boolean flag = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            flag = true;

        } catch (IOException e) {
            //e.printStackTrace();
            Log.d(TAG, e.toString());
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }
        }
        return flag;
    }

    public static boolean saveStringToFile(File file, String data) {
        if (Util.isEmpty(data)) return false;

        FileOutputStream fos = null;
        try {
            if (file != null) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            byte[] bytes = data.getBytes();
            fos.write(bytes);
            return true;
        } catch (Exception e) {
            Log.w(TAG, e);
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.w(TAG, e);

                }
        }
        return false;
    }


    public static String readFromFile(File file) {
        if (!file.exists()) return null;
        StringBuilder text = new StringBuilder();

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();

        } catch (IOException e) {
            Log.w(TAG, "io exception in reading file");
        }
        return text.toString();
    }

    /**
     * Return the size of a directory in bytes
     */
    public static long dirSize(File dir) {

        if (dir.exists()) {
            long result = 0;
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // Recursive call if it's a directory
                if (fileList[i].isDirectory()) {
                    result += dirSize(fileList[i]);
                } else {
                    // Sum the file size in bytes
                    result += fileList[i].length();
                }
            }
            return result; // return the file size
        }
        return 0;
    }

    public static boolean createDirectory(File file) {

        if (file != null) {
            return file.mkdirs() || file.isDirectory();
        } else return false;

        /*if (file.isDirectory()) {
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            File parent = file.getParentFile();

            if (!parent.exists()) {
                parent.mkdirs();
            }
        }*/
    }

    public static boolean copyFile(File sourceFile, File desFile) {

        try {
            Files.copy(sourceFile, desFile);
            return true;
        } catch (IOException e) {
            Log.w(TAG, "image file could not be copied @" + e);
        }

        return false;

    }

    public static boolean writableExternalStorage() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWritable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWritable = false;
        }
        return mExternalStorageAvailable && mExternalStorageWritable;
    }

    public static File getAppImageExternalStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (!created) {
                Log.d(TAG, "Directory not created");
                return null;
            }
        }
        return file;
    }

    public static void broadCastToMediaScanner(Context context, File file) {

        Uri contentUri = Uri.fromFile(file);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);


        /*MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });*/
    }

    public static void appendStringToFile(File file, String stringValue) {
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                Log.w(TAG,e);
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.append(stringValue);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            Log.w(TAG,e);
        }
    }
}
