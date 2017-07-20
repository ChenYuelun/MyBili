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
import com.example.chenyuelun.mybili.model.bean.RecBean;
import com.example.chenyuelun.mybili.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/19.
 */

public class RecRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;


    private RecBean data;
    private List<RecBean.DataBean> datas = new ArrayList<>();
    private OnItemClickListener listener;

    public RecRvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rec, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void refresh(List<RecBean.DataBean> recBeans) {
        this.datas.clear();
        this.datas.addAll(recBeans);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_playcount)
        TextView tvPlaycount;
        @BindView(R.id.tv_danmu)
        TextView tvDanmu;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.iv_dialog)
        ImageView ivDialog;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;

        }


        public void setData(int position) {
            final RecBean.DataBean dataBean = datas.get(position);

            Glide.with(context).load(dataBean.getCover()).into(ivCover);
            tvPlaycount.setText(dataBean.getPlay() + "");
            tvDanmu.setText(dataBean.getDanmaku() + "");
            String formatDateTime = UiUtils.getFormatDateTime("mm:ss", dataBean.getDuration() * 1000);
            tvDuration.setText(formatDateTime);
            tvTitle.setText(dataBean.getTitle());

            RecBean.DataBean.TagBean tag = dataBean.getTag();
            if(tag != null) {
                tvTag.setText(dataBean.getTname() + "·" + tag.getTag_name());
            }else {
                tvTag.setText(dataBean.getTname());
            }
            ivDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemMoreClicked(dataBean);
                    }
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemVideoClicked(dataBean);
                    }
                }
            });

        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemVideoClicked(RecBean.DataBean dataBean);

        void onItemMoreClicked(RecBean.DataBean dataBean);
    }



}
