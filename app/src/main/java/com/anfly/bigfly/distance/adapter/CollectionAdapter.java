package com.anfly.bigfly.distance.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.distance.bean.NewsDbBean;
import com.anfly.bigfly.distance.callback.OnCollectionItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private Context context;
    private List<NewsDbBean> list;

    private OnCollectionItemClickListener collectionItemClickListener;

    public void setCollectionItemClickListener(OnCollectionItemClickListener collectionItemClickListener) {
        this.collectionItemClickListener = collectionItemClickListener;
    }

    public void setList(ArrayList<NewsDbBean> list) {
        this.list = list;
    }

    public CollectionAdapter(Context context, List<NewsDbBean> list) {
        this.context = context;
        this.list = list;
    }

    public void clearData() {
        this.list.clear();
    }

    public void loadMore(List<NewsDbBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final NewsDbBean newsBean = list.get(i);
        Glide.with(context).load(newsBean.getThumbnailUrl()).apply(new RequestOptions().circleCrop()).into(viewHolder.iv_thumbnail);
        viewHolder.tv_title.setText(newsBean.getTitle());
        viewHolder.tv_author_name.setText(newsBean.getAuthorName());
        viewHolder.tv_date.setText(newsBean.getDate());
        viewHolder.tv_category.setText(newsBean.getCategory());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionItemClickListener != null) {
                    collectionItemClickListener.onCollectionItemClickListener(i, newsBean);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (collectionItemClickListener != null) {
                    collectionItemClickListener.onCollectionItemLongClickListener(i, newsBean);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_thumbnail;
        public TextView tv_title;
        public TextView tv_author_name;
        public TextView tv_category;
        public TextView tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_thumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_author_name = (TextView) itemView.findViewById(R.id.tv_author_name);
            this.tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
