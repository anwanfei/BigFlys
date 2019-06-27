package com.anfly.bigfly.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.distance.activity.WebViewActivity;
import com.anfly.bigfly.home.bean.BannerBean;
import com.anfly.bigfly.home.bean.HomeListBean;
import com.anfly.bigfly.home.callback.OnHomeBannerClickListener;
import com.anfly.bigfly.home.callback.OnHomeItemClickListener;
import com.anfly.bigfly.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Anfly
 * @date 2019/4/23
 * description：
 */
public class HomeListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HomeListBean.DataBean.DatasBean> list;
    private List<BannerBean.DataBean> homeBanners;
    private OnHomeItemClickListener onHmeItemClickListener;
    private OnHomeBannerClickListener onHomeBannerClickListener;
    private int TYPE_BANNER = 0;
    private int TYPE_LIST = 1;
    private ArrayList<String> images;
    private ArrayList<String> titles;
    private int newPosition;

    public void setOnHmeItemClickListener(OnHomeItemClickListener onHmeItemClickListener) {
        this.onHmeItemClickListener = onHmeItemClickListener;
    }

    public void setOnHomeBannerClickListener(OnHomeBannerClickListener onHomeBannerClickListener) {
        this.onHomeBannerClickListener = onHomeBannerClickListener;
    }

    public void setList(ArrayList<HomeListBean.DataBean.DatasBean> list) {
        this.list = list;
    }

    public HomeListsAdapter(Context context, List<HomeListBean.DataBean.DatasBean> list, List<BannerBean.DataBean> homeBanners) {
        this.context = context;
        this.list = list;
        this.homeBanners = homeBanners;
    }

    public void clearData() {
        this.list.clear();
    }

    public void loadMore(List<HomeListBean.DataBean.DatasBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //创建一个自定义ViewHolder
        if (i == TYPE_BANNER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null, false);
            ViewBannerHolder viewHolder = new ViewBannerHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_list, null, false);
            ViewListHolder viewHolder = new ViewListHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //将数据与界面绑定
        int itemViewType = getItemViewType(i);
        if (itemViewType == TYPE_BANNER) {
            HomeListsAdapter.ViewBannerHolder bannerHolder = (HomeListsAdapter.ViewBannerHolder) viewHolder;

            images = new ArrayList<>();
            titles = new ArrayList<>();
            for (int j = 0; j < homeBanners.size(); j++) {
                images.add(homeBanners.get(j).getImagePath());
                titles.add(homeBanners.get(j).getTitle());
            }
            //设置图片加载器
            bannerHolder.banner.setImageLoader(new GlideImageLoader());
            //设置标题集合（当banner样式有显示title时）
            bannerHolder.banner.setBannerTitles(titles);
            //设置图片集合
            bannerHolder.banner.setImages(images);
            //显示圆形指示器和标题（水平显示）
            bannerHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            //banner设置方法全部调用完毕时最后调用
            bannerHolder.banner.start();
            bannerHolder.banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", homeBanners.get(position).getUrl());
                    Intent intent = new Intent(context, WebViewActivity.class);
                    if (bundle != null) {
                        intent.putExtra("data", bundle);
                    }
                    context.startActivity(intent);

                }
            });
        } else {

            if (homeBanners.size() > 0) {
                newPosition = i - 1;
            }

            HomeListsAdapter.ViewListHolder listHolder = (ViewListHolder) viewHolder;
            final HomeListBean.DataBean.DatasBean homeList = list.get(newPosition);
            listHolder.tvAuthorName.setText(homeList.getAuthor());
            listHolder.tvDescribe.setText(homeList.getChapterName());
            listHolder.tvTitle.setText(homeList.getTitle());
            listHolder.tvNiceDate.setText(homeList.getNiceDate());
            listHolder.tvCategory.setText(homeList.getSuperChapterName());

            listHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHmeItemClickListener != null) {
                        onHmeItemClickListener.onHomeItemClickListener(newPosition, homeList);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public class ViewBannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        ViewBannerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_niceDate)
        TextView tvNiceDate;
        @BindView(R.id.tv_category)
        TextView tvCategory;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
