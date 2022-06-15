package com.mygdx.mongojocs.midletemu;

public class Displayable {

    public boolean isShown()
    {
        return Display.currentDisplayable == this;
    }
}
