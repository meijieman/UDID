package com.major.udid;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * @desc: 自己写一个文件保存
 * @author: Major
 * @since: 2017/12/20 22:21
 * @see {http://android-developers.blogspot.com/2011/03/identifying-app-installations.html}
 */
public class Installation{

    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context){
        if(sID == null){
            File installation = new File(context.getFilesDir(), INSTALLATION);
            Log.w("tag_installation", "installation path " + installation.getAbsolutePath());
            try{
                if(!installation.exists()){
                    writeInstallationFile(installation);
                }
                sID = readInstallationFile(installation);
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException{
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int)f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException{
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}
