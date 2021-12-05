// IMyMusicInterface.aidl
package com.itis.androidlabproject;

// Declare any non-default types here with import statements

interface IMyMusicInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void playPreviousTrack();
    void playNextTrack();
    void pauseTrack();
    void playTrack();
    void setTrack(int id);
}
