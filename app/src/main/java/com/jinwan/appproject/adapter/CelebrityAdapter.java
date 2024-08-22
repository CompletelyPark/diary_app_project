package com.jinwan.appproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.list.Celebrity;

import java.util.List;

public class CelebrityAdapter extends RecyclerView.Adapter<CelebrityAdapter.CelebrityViewHolder> {

    private final List<Celebrity> celebrities;

    public CelebrityAdapter(List<Celebrity> celebrities) {
        this.celebrities = celebrities;
    }

    @NonNull
    @Override
    public CelebrityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_celebrity, parent, false);
        return new CelebrityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CelebrityViewHolder holder, int position) {
        Celebrity celebrity = celebrities.get(position);
        holder.celebrityMemo.setText(celebrity.getMemo());
        holder.celebrityDay.setText(celebrity.getDate());
    }

    @Override
    public int getItemCount() {
        return celebrities.size();
    }

    public static class CelebrityViewHolder extends RecyclerView.ViewHolder {
        TextView celebrityMemo;
        TextView celebrityDay;

        public CelebrityViewHolder(@NonNull View itemView) {
            super(itemView);
            celebrityMemo = itemView.findViewById(R.id.celebrity_memo);
            celebrityDay = itemView.findViewById(R.id.celebrity_day);
        }
    }
}

