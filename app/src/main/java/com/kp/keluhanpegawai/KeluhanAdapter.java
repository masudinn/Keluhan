package com.kp.keluhanpegawai;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KeluhanAdapter  extends RecyclerView.Adapter<KeluhanAdapter.ViewHolder> {

    private final ArrayList<KeluhanModel> listVideo = new ArrayList<>();

    public void setData(ArrayList<KeluhanModel> items) {
        listVideo.clear();
        listVideo.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_keluhan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listVideo.get(position), listVideo);

    }

    @Override
    public int getItemCount() {
        return listVideo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_keluhan, tv_lokasi,tv_tanggal;
        CardView card_keluhan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_keluhan = itemView.findViewById(R.id.cardkeluhan);
            tv_keluhan = itemView.findViewById(R.id.keluhanpelapor);
            tv_lokasi = itemView.findViewById(R.id.lokasikejadian);
            tv_tanggal = itemView.findViewById(R.id.waktulapor);
        }

        public void bind(KeluhanModel keluhanModel, ArrayList<KeluhanModel> listVideo) {

            tv_keluhan.setText(keluhanModel.getJudulkeluhan());
            tv_lokasi.setText(keluhanModel.getLokasi());
            tv_tanggal.setText(keluhanModel.getTanggallapor());

            card_keluhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailKeluhanActivity.class);
                    intent.putExtra(DetailKeluhanActivity.EXTRAKELUHAN,keluhanModel);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
