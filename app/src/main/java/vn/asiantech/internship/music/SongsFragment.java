package vn.asiantech.internship.music;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display songs in viewPager.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class SongsFragment extends Fragment {
    private final List<Song> mSongs = new ArrayList<>();
    private OnGetSongListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnGetSongListener) activity;
    }

    public static SongsFragment newInstance() {
        return new SongsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_music, container, false);
        RecyclerView musicRecyclerView = (RecyclerView) view.findViewById(R.id.musicRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        musicRecyclerView.setLayoutManager(linearLayoutManager);
        mSongs.addAll(((MusicActivity) getActivity()).getSongs());
        MusicRecyclerViewAdapter adapter = new MusicRecyclerViewAdapter(mSongs, new MusicRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song, int position) {
                getSong(position);
            }
        });
        musicRecyclerView.setAdapter(adapter);
        return view;
    }

    private void getSong(int position) {
        mListener.onGetSong(position);
    }

    /*
     * Used to get song and position when click to song list
     */
    interface OnGetSongListener {
        void onGetSong(int position);
    }
}
