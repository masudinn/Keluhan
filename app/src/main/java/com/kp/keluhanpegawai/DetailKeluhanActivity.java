package com.kp.keluhanpegawai;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kp.keluhanpegawai.databinding.ActivityDetailKeluhanBinding;

public class DetailKeluhanActivity extends AppCompatActivity {

    public static final String EXTRAKELUHAN = "keluhan";
    ActivityDetailKeluhanBinding binding;
    KeluhanModel keluhanModel;
    KeluhanAdapter keluhanAdapter;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailKeluhanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        keluhanModel = getIntent().getParcelableExtra(EXTRAKELUHAN);
        binding.progressBar.setVisibility(View.VISIBLE);

        binding.judulkeluhandetail.setText(keluhanModel.getJudulkeluhan());
        binding.lokasikeluhandetail.setText(keluhanModel.getLokasi());
        binding.pelapordetail.setText(keluhanModel.getPelapor());
        binding.waktulapordetail.setText(keluhanModel.getTanggallapor());
        binding.isilaporandetail.setText(keluhanModel.getIsikeluhan());


        binding.deleteDetailVidio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        MediaController mediaController = new MediaController(DetailKeluhanActivity.this);
        mediaController.setAnchorView(videoView);

        Uri videoUri = Uri.parse(keluhanModel.getVideo());
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        binding.videoDetail.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }
}