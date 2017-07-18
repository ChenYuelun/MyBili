package com.example.chenyuelun.mybili.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.model.bean.LiveBean;
import com.example.chenyuelun.mybili.utils.UiUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/18.
 */

public class LiveRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;



    private LiveBean.DataBean data;

    public LiveRvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder baseHolder = null;
        if (viewType == 0) {
            baseHolder = new BannerHolder(LayoutInflater.from(context).inflate(R.layout.item_banner_live, parent, false));
        } else if (viewType == 1) {
            baseHolder = new TypeHolder(LayoutInflater.from(context).inflate(R.layout.item_type_live, parent, false));
        } else if(viewType == getItemCount()-1) {
            baseHolder = new FootHolder(LayoutInflater.from(context).inflate(R.layout.item_foot_live, parent, false));
        } else {
            baseHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_live, parent, false));
        }
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder viewHolder = (BaseHolder) holder;
        viewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getPartitions().size()+3;
    }

    public void refesh(LiveBean.DataBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(int partitionsBean) {

        }
    }


    class ViewHolder extends BaseHolder {
        @BindView(R.id.iv_entrance_icon)
        ImageView ivEntranceIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ll_zhuanqu)
        LinearLayout llZhuanqu;
        @BindView(R.id.rv_live_item)
        RecyclerView rvLiveItem;
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.tv_refesh)
        TextView tvRefesh;

        public ViewHolder(View itemView) {
            super(itemView);
        }


        public void setData(int position) {
            super.setData(position);
            int realPosition = position-2;
            LiveBean.DataBean.PartitionsBean partitionsBean = data.getPartitions().get(realPosition);
            LiveBean.DataBean.PartitionsBean.PartitionBean partition = partitionsBean.getPartition();
            Glide.with(context).load(partition.getSub_icon().getSrc()).into(ivEntranceIcon);
            int count = partition.getCount();
            tvCount.setText(UiUtils.getString(context, count + "", R.string.count_live));
            tvName.setText(partition.getName());
            LiveItemRvAdapter liveItemRvAdapter = new LiveItemRvAdapter(context, partitionsBean.getLives());
            rvLiveItem.setAdapter(liveItemRvAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            rvLiveItem.setLayoutManager(gridLayoutManager);
        }
    }

    class BannerHolder extends BaseHolder {
        @BindView(R.id.banner)
        Banner banner;
        public BannerHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            super.setData(position);
            initBanner(data.getBanner());
        }


        //设置Banner
        private void initBanner(List<LiveBean.DataBean.BannerBean> banner) {
            List<String> images = new ArrayList<>();
            for (int i = 0; i < banner.size(); i++) {
                images.add(banner.get(i).getImg());
            }
            //设置图片加载器
            this.banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            this.banner.setImages(images);
            //设置指示器位置
            this.banner.setIndicatorGravity(BannerConfig.RIGHT);
            //banner设置方法全部调用完毕时最后调用
            this.banner.start();
        }



        public class GlideImageLoader extends ImageLoader {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
//            Picasso.with(context).load((String) path).into(imageView);
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(params);
                return imageView;
            }


        }
    }

    class TypeHolder extends BaseHolder{

        public TypeHolder(View itemView) {
            super(itemView);
        }
    }

    class FootHolder extends BaseHolder{

        public FootHolder(View itemView) {
            super(itemView);
        }
    }
}
