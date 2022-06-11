package com.mygdx.mongojocs;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.mongojocs.Launcher;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		InterfaceImplementation interfaceImplementation = new InterfaceImplementation(this);

		initialize(new Launcher(interfaceImplementation), config);
	}
}
