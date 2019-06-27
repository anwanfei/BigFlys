package com.anfly.bigfly.guidewelcom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.anfly.bigfly.R;

import java.util.List;

/**
 * @author Anfly
 * @date 2019/5/21
 * description：
 */
public class GuideAdapter extends PagerAdapter {

    private Context mContext;
    private List<Integer> mList;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public GuideAdapter(Context mContext, List<Integer> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_guide, null);
        final Button btn_welcome = (Button) view.findViewById(R.id.btn_welcome);
        ImageView iv_welcome = (ImageView) view.findViewById(R.id.iv_welcome);
        iv_welcome.setImageResource(mList.get(position));
        if (position == mList.size() - 1) {
            btn_welcome.setVisibility(View.VISIBLE);
        }
        container.addView(view);
        btn_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickListener(btn_welcome);
            }
        });
        return view;
    }
}
