package vn.asiantech.internship.ui.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

import static vn.asiantech.internship.R.id.seekBar;

/**
 * MusicActivity created by Thanh Thien
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MusicActivity.class.getSimpleName();

    private SeekBar mSeekBar;
    private ImageView mImgPause;
    private TextView mTvCurrentTime;
    private TextView mTvDurationTime;

    private int mLength;
    private int mPlay;
    private ArrayList<Music> mSongs = new ArrayList<>();
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            processTime(intent);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mImgPause = (ImageView) findViewById(R.id.imgPause);
        mSeekBar = (SeekBar) findViewById(seekBar);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvDurationTime = (TextView) findViewById(R.id.tvDurationTime);
        addData();
        initIntentFilter();

        // Init Listener
        findViewById(R.id.imgPause).setOnClickListener(this);
        findViewById(R.id.imgNext).setOnClickListener(this);
        findViewById(R.id.imgPrevious).setOnClickListener(this);
        findViewById(R.id.imgShuffle).setOnClickListener(this);
        findViewById(R.id.imgRepeat).setOnClickListener(this);
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
        registerReceiver(mBroadcastReceiver, filter);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent playIntent = new Intent(MusicActivity.this, NotificationServiceMusic.class);
                playIntent.putExtra("chooseTime", seekBar.getProgress());
                playIntent.setAction(Action.SEEK_TO.getValue());
                startService(playIntent);
            }
        });
    }

    private void processTime(Intent intent) {
        if (mLength == 0) {
            mLength = Integer.parseInt(intent.getStringExtra("time"));
            mSeekBar.setMax(mLength);
            mSeekBar.setProgress(0);
            mTvDurationTime.setText(Action.TIME.getTime(mLength));
            return;
        }
        int position = Integer.parseInt(intent.getStringExtra("second"));
        mSeekBar.setProgress(position);
        mTvCurrentTime.setText(Action.TIME.getTime(position));
        Intent progressBarIntent = new Intent(MusicActivity.this, NotificationServiceMusic.class);
        progressBarIntent.putExtra("position", position);
        progressBarIntent.setAction(Action.PROGRESSBAR.getValue());
        startService(progressBarIntent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NotificationServiceMusic.class);
        switch (view.getId()) {
            case R.id.imgPause:
                if (mPlay == 0) {
                    mPlay = 1;
                    mImgPause.setImageResource(R.drawable.vector_pause);
                    intent.putParcelableArrayListExtra(Action.INTENT.getValue(), mSongs);
                    intent.setAction(Action.START.getValue());
                } else if (mPlay == 1) {
                    mPlay = 2;
                    mImgPause.setImageResource(R.drawable.vector_play);
                    intent.setAction(Action.PAUSE.getValue());
                } else {
                    mPlay = 1;
                    mImgPause.setImageResource(R.drawable.vector_pause);
                    intent.setAction(Action.PAUSE.getValue());
                }
                startService(intent);
                break;
            case R.id.imgNext:
                if (mPlay != 0) {
                    intent.setAction(Action.NEXT.getValue());
                    startService(intent);
                } else {
                    showTip();
                }
                break;
            case R.id.imgPrevious:
                if (mPlay != 0) {
                    intent.setAction(Action.PREVIOUS.getValue());
                    startService(intent);
                } else {
                    showTip();
                }
                break;
            case R.id.imgShuffle:
                intent.setAction(Action.SHUFFLE.getValue());
                startService(intent);
                break;
            case R.id.imgRepeat:
                intent.setAction(Action.REPEAT.getValue());
                startService(intent);
                break;
        }
    }

    private void showTip() {
        Toast.makeText(this, getString(R.string.error_message_you_must_play_first), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    private void addData() {
        mSongs.add(new Music("Stay (Acoustic)",
                "Zedd ft Alessia Cara",
                "http://vip.img.cdn.keeng.vn/medias/images/images_thumb/f_medias/singer/2012/07/27/f02fd92988c427d6c3363594c157b6a7c1a62daa_103_103.jpg",
                "http://hot5.medias.keeng.vn/mp3/sas_01/songv3/Uni1206/Stay_Acoustic.mp3"));
        mSongs.add(new Music(
                "Don't Look Down",
                "Martin Garrix ft Usher",
                "http://vip.img.cdn.keeng.vn/sata11/images/images_thumb/f_sata11/singer/2017/05/30/Ln6RoalPYNg3EP0pcGl1592d6df7588d6_103_103.jpg",
                "http://hot5.medias.keeng.vn/mp3/sata04/songv3/2017/05/29/Vr5nMj34rpwb4sNXOGOM592b793b2d5ba.mp3"
        ));
        mSongs.add(new Music(
                "Swish Swish",
                "Katy Perry ft Nicki Minaj",
                "http://vip.img.cdn.keeng.vn/sata11/images/images_thumb/f_sata11/singer/2017/04/26/hdn8fSltRRRUnfrNgPOQ590054708c2fa_103_103.jpg",
                "http://hot4.medias.keeng.vn/mp3/sata10/songv3/2017/05/22/j9xne09Q62csEmQEb8qh5922a987dee3f.mp3"
        ));
        mSongs.add(new Music(
                "Bad Liar",
                "Selena Gomez",
                "http://vip.img.cdn.keeng.vn/medias/images/images_thumb/f_medias_6/singer/2014/10/13/b5cd6b2b2e4d5b6fca2695cac29d908ce5d58639_103_103.jpg",
                "http://hot4.medias.keeng.vn/mp3/sata07/songv3/2017/05/19/dD7cIyqVFkOviCIpVdlE591e666439e1c.mp3"
        ));
    }
}
