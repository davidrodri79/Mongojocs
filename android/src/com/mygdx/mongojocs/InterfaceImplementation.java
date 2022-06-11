package com.mygdx.mongojocs;

import android.content.Context;

import java.io.File;

public class InterfaceImplementation implements MyInterface {

        private Context context;

        public InterfaceImplementation(Context context)
        {
            this.context = context;
        }

        @Override
        public void manipulateContext()
        {

        }

        @Override
        public void manipulateContextWithExtraParams(String example, int example2) {

        }

        @Override
        public File getAppFilesFolder()
        {
            return context.getFilesDir();
        }

}
