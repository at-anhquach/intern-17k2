package vn.asiantech.internship.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Used to display main music fragment .
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class MainFragment extends Fragment {
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_music, container, false);
        ImageView imgMain = (ImageView) view.findViewById(R.id.imgMain);
        Animation animFade = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        imgMain.startAnimation(animFade);
        TextView tvNameSong = (TextView) view.findViewById(R.id.tvNameSong);
        if (getArguments() != null) {
            tvNameSong.setText(getArguments().getString("songName"));
        }
        return view;
    }
}
