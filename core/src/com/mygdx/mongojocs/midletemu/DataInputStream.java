package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.InputStream;

public class DataInputStream {

    byte[] data;
    int dataIndex;

    public DataInputStream(String name) {
        FileHandle file = Gdx.files.internal(MIDlet.assetsFolder+"/"+name.substring(1));
        data = file.readBytes();
        dataIndex = 0;
    }

    public byte readByte() {
        byte b = -1;
        if(data != null && dataIndex < data.length)
        {
            b = data[dataIndex];
            dataIndex++;
        }
        return b;
    }

    public void close() {
        data = null;
        System.gc();
    }

    public short readShort() {
        short s = -1;
        if(data != null && dataIndex < data.length - 1)
        {
            s = (short)(data[dataIndex + 1] | (data[dataIndex] << 8));
            dataIndex += 2;
        }
        return s;
    }
}
