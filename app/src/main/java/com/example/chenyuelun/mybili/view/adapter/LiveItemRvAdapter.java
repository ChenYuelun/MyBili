package com.example.chenyuelun.mybili.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.mybili.R;
import com.example.chenyuelun.mybili.model.bean.LiveBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/18.
 */

public class LiveItemRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<LiveBean.DataBean.PartitionsBean.LivesBean> datas;


    public LiveItemRvAdapter(Context context, List<LiveBean.DataBean.PartitionsBean.LivesBean> lives) {
        this.context = context;
        this.datas = lives;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_live_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_src)
        ImageView ivSrc;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_owner_name)
        TextView tvOwnerName;
        @BindView(R.id.tv_online)
        TextView tvOnline;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(LiveBean.DataBean.PartitionsBean.LivesBean livesBean) {
            Glide.with(context).load(livesBean.getCover().getSrc()).into(ivSrc);
            tvTitle.setText(livesBean.getTitle());
            tvOwnerName.setText(livesBean.getOwner().getName());
            tvOnline.setText(livesBean.getOnline()+"");
        }
    }
}
