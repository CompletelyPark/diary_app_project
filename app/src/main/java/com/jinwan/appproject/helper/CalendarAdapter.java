package com.jinwan.appproject.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jinwan.appproject.R;
import java.util.List;

/*
    동작 방식
  1. ViewHolder 생성 - ReCyclerView 는 화면에 표시할 필요가 있는 항목에 대해 onCreateViewHolder() 를 호출해 ViewHolder 를 생성
  2. data binding - 생성된 ViewHolder 는 onBindViewHolder() 를 통해 days 리스트의 데이터와 연결
                    position 에 해당하는 날짜가 dayText에 설정
  3. check listSize - RecyclerView 는 getItemCount() 를 호출 해 표시할 아이템의 수를 확인한다. 이 수는 days 리스트의 크기와 동일
 */


// 해당 Adapter class 는 간단한 구조로, RecyclerView 와 함께 작동하여 달력의 날짜를 그리드 형태로 표시하는 기능을 제공
// 각 날짜는 days 리스트에서 가져오며, 해당 리스트는 외부에서 adapter로 전달
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

//  달력에 표시할 날짜들의 목록을 저장하는 List<String> 타입의 변수
//  리스트에 달력의 각 날짜가 문자열로 저장
    public List<String> days;

//  어댑터가 생성될 때 days 리스트를 인자로 받아 필드에 저장한다
//  해당 리스트는 달력의 일자를 포함하는 리스트
    public CalendarAdapter(List<String> days) {
        this.days = days;
    }

    //  RecyclerView 가 새로운 아이템 뷰를 필요 할 때 호출
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//      LayoutInflater 를 사용해 imem_calendar_day.xml 을 읽어와 view 객체로 만든다
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_day, parent, false);

//      만들어진 View 객체를 포함하는 CalendarViewHolder 객체를 생성하고 반환
        return new CalendarViewHolder(view);
    }


    //  RecyclerView 가 특정 위치의 데이터를 viewholder에 바인딩 할 때 호출
    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.CalendarViewHolder holder, int position) {
//      position 파라미터는 현재 항목의 위치, days 리스트에는 해당 위치에 있는 날짜를 가져와서 CalendarViewHolder 의 TextView 에 설정
        holder.dayText.setText(days.get(position));
    }

    //  RecyclerView 가 몇 개의 항목을 표시해야 하는지 알아내기 위해 호출
    @Override
    public int getItemCount() {
//      days 리스트의 크기를 반환하며, 이 크기는 달력에서 표시할 총 날짜의 수와 동일
        return days.size();
    }

    // RecyclerView 의 개발 아이템을 재활용 하기 위한 뷰홀더 클래스
    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
//      각 날짜를 표시하기 위한 textView - item_calendar_day.xml layout 의 TextView 와 link
        TextView dayText;

//      constructor
//      itemView 에 TextView를 찾아서 연결
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
        }
    }
}
