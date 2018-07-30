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
public class PhrasesFragment extends Fragment {
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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("hello","luttii",R.raw.phrase_are_you_coming));
        words.add(new Word("hi","otiiko",R.raw.phrase_come_here));
        words.add(new Word("how are you","tolookppsu",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm fine","oyyisa",R.raw.phrase_im_coming));
        words.add(new Word("thank you","massokka",R.raw.phrase_im_feeling_good));
        words.add(new Word("six","temmokka",R.raw.phrase_lets_go));
        words.add(new Word("seven","kenekaku",R.raw.phrase_my_name_is));
        words.add(new Word("eigh","kawinta",R.raw.phrase_what_is_your_name));
        words.add(new Word("nine","wo'ee",R.raw.phrase_where_are_you_going));
        words.add(new Word("ten","na'aacha",R.raw.phrase_yes_im_coming));
//        WordAdapter adapter = new WordAdapter(this,  words,R.color.category_phrases);

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
//        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);

//            Toast.makeText(NumbersActivity.this,"click audio",Toast.LENGTH_LONG).show();
                releaseMediaPlayer();
//                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
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
