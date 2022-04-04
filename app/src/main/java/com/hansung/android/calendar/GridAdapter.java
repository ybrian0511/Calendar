package com.hansung.android.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<String> mdays = new ArrayList<String>();

    public GridAdapter(Context context, int resource, ArrayList<String> dayList) {
        mContext = context;
        mdays = dayList;
        mResource = resource;
    }

    @Override
    public int getCount() { // GridAdapter 클래스가 관리하는 항목의 총 개수를 반환
        return mdays.size();
    }

    @Override
    public String getItem(int position) { // GridAdapter 클래스가 관리하는 항목의 중에서 position 위치의 항목을 반환
        return mdays.get(position);
    }

    @Override
    public long getItemId(int position) { // 항목 id를 항목의 위치로 간주함
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) { // 해당 항목 뷰가 이전에 생성된 적이 없는 경우
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 항목 뷰를 정의한 xml 리소스(여기서는 mResource 값)으로부터 항목 뷰 객체를 메모리로 로드
            convertView = inflater.inflate(mResource,parent,false);
        }

        //ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
        //layoutParams.height = (int) (parent.getHeight() *0.166666666);

        TextView day =(TextView) convertView.findViewById(R.id.day);
        day.setText(mdays.get(position));

        return convertView;
    }
}
