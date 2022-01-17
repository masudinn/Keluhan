package com.kp.keluhanpegawai;

import android.os.Parcel;
import android.os.Parcelable;

public class KeluhanModel implements Parcelable {

    private String judulkeluhan;
    private String pelapor;
    private String lokasi;
    private String tanggallapor;
    private String isikeluhan;
    private String uid;
    private String video;


    public KeluhanModel(){

    }

    protected KeluhanModel(Parcel in) {
        judulkeluhan = in.readString();
        pelapor = in.readString();
        lokasi = in.readString();
        tanggallapor = in.readString();
        isikeluhan = in.readString();
        uid = in.readString();
        video = in.readString();
    }

    public static final Creator<KeluhanModel> CREATOR = new Creator<KeluhanModel>() {
        @Override
        public KeluhanModel createFromParcel(Parcel in) {
            return new KeluhanModel(in);
        }

        @Override
        public KeluhanModel[] newArray(int size) {
            return new KeluhanModel[size];
        }
    };

    public String getJudulkeluhan() {
        return judulkeluhan;
    }

    public void setJudulkeluhan(String judulkeluhan) {
        this.judulkeluhan = judulkeluhan;
    }

    public String getPelapor() {
        return pelapor;
    }

    public void setPelapor(String pelapor) {
        this.pelapor = pelapor;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggallapor() {
        return tanggallapor;
    }

    public void setTanggallapor(String tanggallapor) {
        this.tanggallapor = tanggallapor;
    }

    public String getIsikeluhan() {
        return isikeluhan;
    }

    public void setIsikeluhan(String isikeluhan) {
        this.isikeluhan = isikeluhan;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judulkeluhan);
        dest.writeString(pelapor);
        dest.writeString(lokasi);
        dest.writeString(tanggallapor);
        dest.writeString(isikeluhan);
        dest.writeString(uid);
        dest.writeString(video);
    }
}
