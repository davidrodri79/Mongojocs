package com.mygdx.mongojocs.midletemu;

public class RecordStore {
    
    public static RecordStore openRecordStore(String s, boolean b) {
        return new RecordStore();
    }

    public int getNumRecords() {
        return 0;
    }

    public void addRecord(byte[] prefsData, int i, int length) {
    }

    public void setRecord(int i, byte[] prefsData, int i1, int length) {
    }

    public byte[] getRecord(int i) {
        return new byte[0];
    }

    public void closeRecordStore() {
    }
}
