package com.kp.keluhanpegawai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kp.keluhanpegawai.databinding.ActivityDaftarKeluhanBinding;

public class DaftarKeluhanActivity extends AppCompatActivity {

    ActivityDaftarKeluhanBinding binding;
    KeluhanAdapter keluhanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarKeluhanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fbadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKeluhan();
            }
        });
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        KeluhanViewModel viewModel = new ViewModelProvider(this).get(KeluhanViewModel.class);

        binding.progressbar.setVisibility(View.VISIBLE);
        viewModel.setListVidio();
        viewModel.getVideo().observe(this,keluhanModels -> {
            if (keluhanModels.size() > 0) {
                binding.nodata.setVisibility(View.GONE);
                keluhanAdapter.setData(keluhanModels);
            } else {
                binding.nodata.setVisibility(View.VISIBLE);
            }
            binding.progressbar.setVisibility(View.GONE);
        });

    }

    private void initRecyclerView() {
        binding.rvDaftarkeluhan.setLayoutManager(new LinearLayoutManager(this));
        keluhanAdapter = new KeluhanAdapter();
        binding.rvDaftarkeluhan.setAdapter(keluhanAdapter);

    }

    private void addKeluhan() {
        Intent intent = new Intent(DaftarKeluhanActivity.this, TambahKeluhanActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}