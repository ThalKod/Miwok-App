package com.example.android.miwok;

import android.media.MediaPlayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Thal Marc on 1/16/2017.
 */

public class NumbersFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager am;

    AudioManager.OnAudioFocusChangeListener audioListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            //In case audio focus is loss
            if(i == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
                //Listener to wait the music stop playing
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaplayer();
                    }
                });
            }
            if(i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                mMediaPlayer.pause();
            }
            if(i==AudioManager.AUDIOFOCUS_LOSS){
                mMediaPlayer.stop();
                releaseMediaplayer();
                am.abandonAudioFocus(this);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.display,container,false);

        am = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);

        // Create an arrayList to store English Word

        final ArrayList<Word> WordArrayList = new ArrayList<Word>();
        WordArrayList.add(new Word("One","Lutti",R.drawable.number_one,R.raw.number_one));
        WordArrayList.add(new Word("Two","Otiiko",R.drawable.number_two,R.raw.number_two));
        WordArrayList.add(new Word("Three","tolookosu",R.drawable.number_three,R.raw.number_three));
        WordArrayList.add(new Word("Four","oyyisa",R.drawable.number_four,R.raw.number_four));
        WordArrayList.add(new Word("Five","massoka",R.drawable.number_five,R.raw.number_five));
        WordArrayList.add(new Word("Six","temmokka",R.drawable.number_six,R.raw.number_six));
        WordArrayList.add(new Word("Seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        WordArrayList.add(new Word("Height","kawinta",R.drawable.number_eight,R.raw.number_eight));
        WordArrayList.add(new Word("Nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        WordArrayList.add(new Word("Ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(getActivity(),WordArrayList,R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // displayut file.
        final ListView listView = (ListView) rootView.findViewById(R.id.List);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Word word = WordArrayList.get(i);
                releaseMediaplayer();
                // Request an Audio Focus Listener and handle different state of audio focus
                int okAudio = am.requestAudioFocus(audioListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(okAudio == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //Play the music
                    mMediaPlayer = MediaPlayer.create(getActivity(),word.getmAudioId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaplayer();
                        }
                    });
                }

            }
        });

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaplayer();
    }

    public void releaseMediaplayer(){
        if(mMediaPlayer !=null){
            mMediaPlayer.release();
            am.abandonAudioFocus(audioListener);
            mMediaPlayer = null;
        }
    }
}
