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

public class FamilyFragment extends Fragment {
    private MediaPlayer mediaPlayer;
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


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.display,container,false);

        am = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);

        final ArrayList<Word> FamilyArrayList = new ArrayList<Word>();
        FamilyArrayList.add(new Word("Father", "apa",R.drawable.family_father,R.raw.family_father));
        FamilyArrayList.add(new Word("mother", "ata",R.drawable.family_mother,R.raw.family_mother));
        FamilyArrayList.add(new Word("daughter", "angsi",R.drawable.family_daughter,R.raw.family_daughter));
        FamilyArrayList.add(new Word("older brother", "tune",R.drawable.family_older_brother,R.raw.family_older_brother));
        FamilyArrayList.add(new Word("younger brother", "taachi",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        FamilyArrayList.add(new Word("older sister", "tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        FamilyArrayList.add(new Word("younger sister", "kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        FamilyArrayList.add(new Word("grand mother", "ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        FamilyArrayList.add(new Word("grand father", "paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        CustomArrayAdapter itemsAdapter = new CustomArrayAdapter(getActivity(),FamilyArrayList,R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // displayut file.
        ListView listView = (ListView) rootView.findViewById(R.id.List);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = FamilyArrayList.get(i);
                releaseMediaplayer();

                // Request an Audio Focus Listener and handle different state of audio focus
                int okAudio = am.requestAudioFocus(audioListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

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

    public void releaseMediaplayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            am.abandonAudioFocus(audioListener);
            mediaPlayer = null;
        }
    }

}
