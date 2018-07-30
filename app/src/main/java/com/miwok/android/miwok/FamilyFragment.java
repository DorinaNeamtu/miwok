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
public class FamilyFragment extends Fragment {
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

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("mother","luttii",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("father","otiiko",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("brother","tolookppsu",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("sister","oyyisa",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandfather","massokka",R.drawable.family_grandfather,R.raw.family_grandfather));
        words.add(new Word("grandmother","temmokka",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("older brother","kenekaku",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("older sister","kawinta",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("daughter","wo'ee",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("sun","na'aacha",R.drawable.family_son,R.raw.family_son));
//        WordAdapter adapter = new WordAdapter(this,  words,R.color.category_family);
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);
//        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);

//            Toast.makeText(NumbersActivity.this,"click audio",Toast.LENGTH_LONG).show();
                releaseMediaPlayer();
//                mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
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
