package com.mygdx.mongojocs.iapplicationemu;

import com.mygdx.mongojocs.parachutist.GameCanvas;

public class AudioPresenter extends MediaPresenter {
    public static final int AUDIO_COMPLETE = 1;

    public static AudioPresenter getAudioPresenter() {
        return new AudioPresenter();
    }

    public void setMediaListener(MediaListener ml) {
    }

    public void setSound(MediaSound mediaSound) {
    }

    public void play() {
    }

    public void stop() {
    }
}
