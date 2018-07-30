package com.miwok.android.miwok;

/**
 * Created by dori on 03.04.2018.
 */

public class Word {
    /**
     * Default translation for the word
     */
    private String mDefaultTranslation;

    /**
     * Image resource ID for the word
     */
    private int mImageResourceId=NO_IMAGE_PROVIDED;

    /**
     * Miwok translation for the word
     */
    private String mMiwokTranslation;

    private static final int NO_IMAGE_PROVIDED=-1;

    private int mAudioResourceId;
    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     */


    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId=audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId=audioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }


    /**
     * Return the image resource ID of the word.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
    *return whether or not there is an image for this word
     **/
    public boolean hasImage(){
        return mImageResourceId!=NO_IMAGE_PROVIDED;

    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;
    }
}
