package com.example.android.miwok;

/**
 * Created by Thal Marc on 1/11/2017.
 */

public class Word {
    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mImageID = 0;
    private int mAudioId;

    public Word(String MiwokTranslation, String DefaultTranslation, int AudioId){
        mMiwokTranslation = MiwokTranslation;
        mDefaultTranslation = DefaultTranslation;
        mAudioId = AudioId;
    }

    public Word(String MiwokTranslation, String DefaultTranslation, int ImageID,int AudioId){
        mMiwokTranslation = MiwokTranslation;
        mDefaultTranslation = DefaultTranslation;
        mImageID = ImageID;
        mAudioId = AudioId;
    }
    
    public String getmiwokTranslation(){
        return mMiwokTranslation;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public  int getImageID(){
        return mImageID;
    }

    public int hasImage(){
        if(mImageID == 0){
            return 0;
        }else{
            return 1;
        }
    }

    public int getmAudioId() {
        return mAudioId;
    }
}
