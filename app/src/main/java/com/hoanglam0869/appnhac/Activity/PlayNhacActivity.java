package com.hoanglam0869.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hoanglam0869.appnhac.Adapter.ViewPagerPlaylistNhac;
import com.hoanglam0869.appnhac.Fragment.Fragment_Dia_Nhac;
import com.hoanglam0869.appnhac.Fragment.Fragment_Play_Danh_Sach_Cac_Bai_Hat;
import com.hoanglam0869.appnhac.Model.BaiHat;
import com.hoanglam0869.appnhac.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarPlayNhac;
    TextView txtTimeSong, txtTotalTimeSong;
    SeekBar skTime;
    ImageView imgPlay, imgRepeat, imgNext, imgPre, imgRandom;
    ViewPager viewPagerPlayNhac;
    public static ArrayList<BaiHat> mangBaiHat = new ArrayList<>();
    public static ViewPagerPlaylistNhac adapterNhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danh_Sach_Cac_Bai_Hat fragment_play_danh_sach_cac_bai_hat;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        GetDataFromIntent();
        init();
        eventClick();
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapterNhac.getItem(1) != null) {
                    if (mangBaiHat.size() > 0) {
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(0).getHinhbaihat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay);
                } else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause);
                }
            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!repeat) {
                    if (checkRandom) {
                        checkRandom = false;
                        imgRepeat.setImageResource(R.drawable.iconsyned);
                        imgRandom.setImageResource(R.drawable.iconshuffle);
                    }
                    imgRepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                } else {
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });

        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkRandom) {
                    if (repeat) {
                        repeat = false;
                        imgRandom.setImageResource(R.drawable.iconshuffled);
                        imgRepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgRandom.setImageResource(R.drawable.iconshuffled);
                    checkRandom = true;
                } else {
                    imgRandom.setImageResource(R.drawable.iconshuffle);
                    checkRandom = false;
                }
            }
        });

        skTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangBaiHat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < mangBaiHat.size()) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat) {
                            if (position == 0) {
                                position = mangBaiHat.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangBaiHat.size() - 1) {
                            position = 0;
                        }
                        new PlayMp3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });

        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangBaiHat.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < mangBaiHat.size()) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position--;
                        if (position < 0) {
                            position = mangBaiHat.size() - 1;
                        }
                        if (repeat) {
                            position += 1;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangBaiHat.clear();
        if (intent != null) {
            if (intent.hasExtra("cakhuc")) {
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangBaiHat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat")) {
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangBaiHat = baiHatArrayList;
            }
        }
    }

    private void init() {
        toolbarPlayNhac = findViewById(R.id.tooBarPlayNhac);
        txtTimeSong = findViewById(R.id.textViewTimeSong);
        txtTotalTimeSong = findViewById(R.id.textViewTotalTimeSong);
        skTime = findViewById(R.id.seekBarSong);
        imgPlay = findViewById(R.id.imageButtonPlay);
        imgRepeat = findViewById(R.id.imageButtonRepeat);
        imgNext = findViewById(R.id.imageButtonNext);
        imgPre = findViewById(R.id.imageButtonPre);
        imgRandom = findViewById(R.id.imageButtonShuffle);
        viewPagerPlayNhac = findViewById(R.id.viewPagerPlayNhac);
        setSupportActionBar(toolbarPlayNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlayNhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangBaiHat.clear();
            }
        });
        toolbarPlayNhac.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_cac_bai_hat = new Fragment_Play_Danh_Sach_Cac_Bai_Hat();
        adapterNhac = new ViewPagerPlaylistNhac(getSupportFragmentManager());
        adapterNhac.AddFragment(fragment_play_danh_sach_cac_bai_hat);
        adapterNhac.AddFragment(fragment_dia_nhac);
        viewPagerPlayNhac.setAdapter(adapterNhac);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapterNhac.getItem(1);
        if (mangBaiHat.size() > 0) {
            getSupportActionBar().setTitle(mangBaiHat.get(0).getTenbaihat());
            new PlayMp3().execute(mangBaiHat.get(0).getLinkbaihat());
            imgPlay.setImageResource(R.drawable.iconpause);
        }
    }

    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        skTime.setMax(mediaPlayer.getDuration());
    }

    private void UpdateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    skTime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next) {
                    if (position < mangBaiHat.size()) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat) {
                            if (position == 0) {
                                position = mangBaiHat.size();
                            }
                            position -= 1;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > mangBaiHat.size() - 1) {
                            position = 0;
                        }
                        new PlayMp3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.PlayNhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                    }
                    imgPre.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    }, 5000);
                    next = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}