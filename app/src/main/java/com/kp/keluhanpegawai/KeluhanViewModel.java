package com.kp.keluhanpegawai;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class KeluhanViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<KeluhanModel>> listVidio = new MutableLiveData<>();
    final ArrayList<KeluhanModel> vidioArrayList = new ArrayList<>();

    private static final String TAG = KeluhanViewModel.class.getSimpleName();

    public void setListVidio(){
        vidioArrayList.clear();

        try {
            FirebaseFirestore
                    .getInstance()
                    .collection("vidio")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                KeluhanModel model = new KeluhanModel();

                                model.setJudulkeluhan("" + documentSnapshot.get("judulkeluhan"));
                                model.setLokasi("" + documentSnapshot.get("lokasi"));
                                model.setTanggallapor("" + documentSnapshot.get("tgl"));
                                model.setIsikeluhan("" + documentSnapshot.get("isi"));
                                model.setPelapor("" + documentSnapshot.get("pelapor"));
                                model.setVideo("" + documentSnapshot.get("vidio"));
                                model.setUid("" + documentSnapshot.get("uid"));

                                vidioArrayList.add(model);
                            }
                            listVidio.postValue(vidioArrayList);
                        }else {
                            Log.e(TAG, task.toString());
                        }
                    });
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public LiveData<ArrayList<KeluhanModel>> getVideo() {
        return listVidio;
    }
}
