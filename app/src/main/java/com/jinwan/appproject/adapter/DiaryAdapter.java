package com.jinwan.appproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jinwan.appproject.R;
import com.jinwan.appproject.list.DiaryEntry;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryOutViewHolder> {
    private final List<DiaryEntry> diaryEntryList;

    public DiaryAdapter(List<DiaryEntry> diaryEntryList) {
        this.diaryEntryList = diaryEntryList;
    }

    @NonNull @Override
    public DiaryOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_diary_out,parent,false);
        return new DiaryOutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryOutViewHolder holder, int position) {
        DiaryEntry diaryEntry = diaryEntryList.get(position);
        holder.imageView_article.setImageDrawable(holder.itemView.getContext().getResources().getDrawable(R.drawable.article_48px));
        holder.title_diary.setText(diaryEntry.getTitle());
        holder.imageView_icon.setImageResource(diaryEntry.getWeatherIcon());
// 날짜 형식 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(diaryEntry.getDate());
        holder.diary_day_time.setText(dateString);  // 날짜를 표시합니다.
    }

    @Override
    public int getItemCount() {
        return diaryEntryList.size();
    }

//  recyclerview 를 사용할 때마다 값을 새로 넣는게 아니라 그냥 값 자체를 추가하는 방식으로 하자
    public void addItem(DiaryEntry diaryEntry_List){
        diaryEntryList.add(diaryEntry_List);
    }

    public static class DiaryOutViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_article;
        TextView title_diary;
        ImageView imageView_icon;
        TextView diary_day_time;

        public DiaryOutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_article = itemView.findViewById(R.id.imageView_article);
            title_diary = itemView.findViewById(R.id.title_diary);
            imageView_icon = itemView.findViewById(R.id.imageView_icon);
            diary_day_time = itemView.findViewById(R.id.diary_day_time);
        }
    }


}
