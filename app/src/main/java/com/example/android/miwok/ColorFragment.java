package com.example.android.miwok;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * Created by Thal Marc on 1/16/2017.
 */

public class ColorFragment extends Fragment {
    private MediaPlayer mediaPlayer ;
    private AudioManager am;

    AudioManager.OnAudioFocusChangeListener audioListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            //In case audio focus is loss
            if(i == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
                //Listener to wait the music stop playing
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaplayer();
                    }
                });
            }
            if(i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                mediaPlayer.pause();
            }
            if(i==AudioManager.AUDIOFOCUS_LOSS){
                mediaPlayer.stop();
                releaseMediaplayer();
                am.abandonAudioFocus(this);
            }
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaplayer();
    }

    public void releaseMediaplayer(){
        if(mediaPlayer !=null){
            mediaPlayer.release();
            am.abandonAudioFocus(audioListener);
            mediaPlayer = null;
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.display,container,false);

        am = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);

        final ArrayList<Word> ColorArrayList = new ArrayList<Word>();
        ColorArrayList.add(new Word("Red", "Weteti",R.drawable.color_red,R.raw.color_red));
        ColorArrayList.add(new Word("Green", "Chokokki",R.drawable.color_green,R.raw.color_green));
        ColorArrayList.add(new Word("Brown", "Takaakki",R.drawable.color_brown,R.raw.color_brown));
        ColorArrayList.add(new Word("Gray", "Topoppi",R.drawable.color_gray,R.raw.color_gray));
        ColorArrayList.add(new Word("Black", "Kululli",R.drawable.color_black,R.raw.color_black));
        ColorArrayList.add(new Word("White", "Kelelli",R.drawable.color_white,R.raw.color_white));
        ColorArrayList.add(new Word("Dusty Yellow", "Topiisa",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        ColorArrayList.add(new Word("Mustard Yellow", "chiwiita",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(getActivity(),ColorArrayList,R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // displayut file.
        ListView listView = (ListView) rootView.findViewById(R.id.List);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = ColorArrayList.get(i);
                releaseMediaplayer();

                // Request an Audio Focus Listener and handle different state of audio focus
                int okAudio = am.requestAudioFocus(audioListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(okAudio == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //Play the music
                    mediaPlayer = MediaPlayer.create(getActivity(),word.getmAudioId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
}
