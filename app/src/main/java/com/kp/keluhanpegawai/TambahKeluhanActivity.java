package com.kp.keluhanpegawai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kp.keluhanpegawai.databinding.ActivityTambahKeluhanBinding;

import java.util.HashMap;
import java.util.Map;

public class TambahKeluhanActivity extends AppCompatActivity {

    ActivityTambahKeluhanBinding binding;
    private static final int REQUEST_FROM_GALLERY_VIDEO = 1002;
    private Uri vidioUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahKeluhanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vidioPickGallery();
            }
        });

        binding.savekeluhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Safe data kedalam database
                ProgressDialog mProgressDialog = new ProgressDialog(TambahKeluhanActivity.this);

                mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                String uid = String.valueOf(System.currentTimeMillis());

                String filePath = "videos/" + "video_"+uid;
                StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePath);
                if(storageReference == null){
                    storageReference.putFile(vidioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();

                            Map<String, Object> document = new HashMap<>();
                            document.put("judulkeluhan",binding.judultambah.getText().toString().trim());
                            document.put("lokasi",binding.lokasitambah.getText().toString().trim());
                            document.put("tgl",binding.waktukejadiantambah.getText().toString().trim());
                            document.put("isi",binding.isikejadiantambah.getText().toString().trim());
                            document.put("pelapor",binding.pelaportambah.getText().toString().trim());
                            document.put("vidio","" + downloadUri);
                            document.put("uid",uid);

                            FirebaseFirestore
                                    .getInstance()
                                    .collection("vidio")
                                    .document(uid)
                                    .set(document)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
//                                                mProgressDialog.dismiss();
                                                showSuccessDialog();
                                            }else {
//                                                mProgressDialog.dismiss();
                                                showFailureDialog();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
//                                            mProgressDialog.dismiss();
                                            Log.e("Exceptions", e.getMessage());
                                        }
                                    });

                        }
                    });

                }else {
                    Toast.makeText(TambahKeluhanActivity.this, "hai", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vidioUri = data.getData();
        setVidioToView();
    }

    private void setVidioToView() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(binding.videoView);

        // set media controller to video view
        binding.videoView.setMediaController(mediaController);
        // set video uri
        binding.videoView.setVideoURI(vidioUri);
        binding.videoView.requestFocus();
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                binding.videoView.pause();
            }
        });
    }
    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Gagal Mengunggah Dokumen")
                .setMessage("Terdapat kesalahan ketika mengunggah dokumen, silahkan periksa koneksi internet anda, dan coba lagi nanti")
                .setIcon(R.drawable.ic_baseline_clear_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Berhasil Mengunggah Dokumen")
                .setMessage("Operasi berhasil")
                .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
                .setPositiveButton("OKE", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    onBackPressed();
                })
                .show();
    }
    private void vidioPickGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Videos"), REQUEST_FROM_GALLERY_VIDEO);
    }
}