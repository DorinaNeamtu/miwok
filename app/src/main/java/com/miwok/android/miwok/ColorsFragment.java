package com.miwok.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                            || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Pause playback
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("red","luttii",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("white","otiiko",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("black","tolookppsu",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("yellow","oyyisa",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("green","massokka",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown","temmokka",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray","kenekaku",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("mustard yellow","kawinta",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
//        WordAdapter adapter = new WordAdapter(this,  words,R.color.category_colors);
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
//        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
                releaseMediaPlayer();
//                mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                mMediaPlayer.start(); // no need to call prepare(); create() does that for you
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });



        return rootView;
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}
