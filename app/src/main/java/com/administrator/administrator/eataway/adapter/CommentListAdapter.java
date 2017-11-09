package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.ReplyActivity;
import com.administrator.administrator.eataway.bean.CommentListBean;
import com.administrator.administrator.eataway.utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/18.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentHolder> {
    private Context context;
    private CommentListBean bean;

    public CommentListAdapter(Context context) {
        this.context = context;
    }

    public CommentListAdapter(Context context, CommentListBean bean) {
        this.bean = bean;
        this.context = context;
    }

    public void setData(CommentListBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public void addData(CommentListBean bean) {
        if (bean.getMsg() != null && bean.getMsg().size() > 0) {
            this.bean.getMsg().addAll(bean.getMsg());
            notifyDataSetChanged();
        }
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_comment_list, null);
        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        initCommentHolder(holder, position);
    }

    private void initCommentHolder(CommentHolder holder, final int position) {
        holder.tvCommentListUserName.setText(bean.getMsg().get(position).getUsername());
        holder.tvCommentListCommentDate.setText(bean.getMsg().get(position).getAddtime());
        holder.tvCommentListCommentContent.setText(bean.getMsg().get(position).getContent());
        if (!bean.getMsg().get(position).getBackpingjia().equals("")){
            holder.btnCommentListReply.setVisibility(View.GONE);
            holder.tvCommentListReply.setVisibility(View.VISIBLE);
            holder.tvCommentListReply.setText(bean.getMsg().get(position).getBackpingjia());
        }else {
            holder.tvCommentListReply.setVisibility(View.GONE);
        }
        GlideUtils.load(context, bean.getMsg().get(position).getHead_photo(), holder.cimgCommentListUserIcon, GlideUtils.Shape.ShopIcon);
        if (!"".equals(bean.getMsg().get(position).getPhoto1())){
            holder.ivCommentListPicFirst.setVisibility(View.VISIBLE);
            GlideUtils.load(context, bean.getMsg().get(position).getPhoto1(), holder.ivCommentListPicFirst, GlideUtils.Shape.ShopIcon);
        }
        if (!"".equals(bean.getMsg().get(position).getPhoto2())) {
            holder.ivCommentListPicSecond.setVisibility(View.VISIBLE);
            GlideUtils.load(context, bean.getMsg().get(position).getPhoto2(), holder.ivCommentListPicSecond, GlideUtils.Shape.ShopIcon);
        }
        int i = Integer.parseInt(bean.getMsg().get(position).getPingjia());
        if (i==1){
            holder.imgStar02.setVisibility(View.GONE);
            holder.imgStar03.setVisibility(View.GONE);
            holder.imgStar04.setVisibility(View.GONE);
            holder.imgStar05.setVisibility(View.GONE);
        }
        if (i==2){
            holder.imgStar03.setVisibility(View.GONE);
            holder.imgStar04.setVisibility(View.GONE);
            holder.imgStar05.setVisibility(View.GONE);
        }
        if (i==3){
            holder.imgStar04.setVisibility(View.GONE);
            holder.imgStar05.setVisibility(View.GONE);
        }
        if (i==4){
            holder.imgStar05.setVisibility(View.GONE);
        }

        holder.btnCommentListReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ReplyActivity.class);
                i.putExtra("eid",bean.getMsg().get(position).getEid());
                i.putExtra("userid",bean.getMsg().get(position).getUid());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean == null || bean.getMsg().size() == 0) {
            return 0;
        }
        return bean.getMsg().size();

    }

    class CommentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cimg_comment_list_user_icon)
        CircleImageView cimgCommentListUserIcon;
        @Bind(R.id.tv_comment_list_user_name)
        TextView tvCommentListUserName;
        @Bind(R.id.img_star_01)
        ImageView imgStar01;
        @Bind(R.id.img_star_02)
        ImageView imgStar02;
        @Bind(R.id.img_star_03)
        ImageView imgStar03;
        @Bind(R.id.img_star_04)
        ImageView imgStar04;
        @Bind(R.id.img_star_05)
        ImageView imgStar05;
        @Bind(R.id.tv_comment_list_comment_date)
        TextView tvCommentListCommentDate;
        @Bind(R.id.tv_comment_list_comment_content)
        TextView tvCommentListCommentContent;
        @Bind(R.id.iv_comment_list_pic_first)
        ImageView ivCommentListPicFirst;
        @Bind(R.id.iv_comment_list_pic_second)
        ImageView ivCommentListPicSecond;
        @Bind(R.id.tv_comment_list_reply)
        TextView tvCommentListReply;
        @Bind(R.id.btn_comment_list_reply)
        Button btnCommentListReply;

        CommentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
