package com.mygdx.mongojocs.midletemu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import jdk.nashorn.internal.runtime.Context;

public class RecordStore {

    File file;
    byte data[] = null;

    public static RecordStore openRecordStore(String s, boolean b)
    {
        RecordStore rs = new RecordStore();
        rs.file = new File(MIDlet.assetsFolder+s);
        if(rs.file.exists())
        {
            try
            {
                FileInputStream fis = new FileInputStream(rs.file);
                rs.data = new byte[(int)rs.file.length()];
                for(int i = 0; i < rs.file.length(); i++) {

                    rs.data[i] = (byte)fis.read();
                }
                fis.close();
            }
            catch(Exception e)
            {

            }
        }
        return rs;
    }

    public int getNumRecords() {
        if(file.exists())
            return 1;
        else {
            if(data != null) return 1; else return 0;
        }
    }

    public void addRecord(byte[] prefsData, int start, int length) {
        data = new byte[length];
        for(int i = 0; i < length; i++)
            data[i] = prefsData[start + i];

        writeFile();
    }

    public void setRecord(int index, byte[] prefsData, int start, int length) {
        data = new byte[length];
        for(int i = 0; i < length; i++)
            data[i] = prefsData[start + i];

        writeFile();
    }

    public byte[] getRecord(int index) {
        return data;
    }

    public void closeRecordStore()
    {

    }

    void writeFile()
    {
        try {
            if (!file.exists())
                file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
