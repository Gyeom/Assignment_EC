package kr.co.ldcc.assignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.Activity.VideoActivity;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.ReplyVo;
import kr.co.ldcc.assignment.Vo.VideoVo;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    String user;
    String profile;
    ArrayList<ReplyVo> replyList;

    public ArrayList<ReplyVo> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<ReplyVo> replyList) {
        this.replyList = replyList;
        notifyDataSetChanged();
    }

    public ReplyAdapter(ArrayList<ReplyVo> replyList, String user, String profile) {
        this.replyList = replyList;
        this.user = user;
        this.profile = profile;
        Log.d("test",replyList.toString());
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_user;
        TextView tv_replyData;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            iv_profile = itemView.findViewById(R.id.iv_profile);
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_replyData = itemView.findViewById(R.id.tv_replyData);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_reply_item,parent,false);
            ViewHolder vh = new ViewHolder(view);
//        Context context = parent.getContext() ;
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
//
//        View view = inflater.inflate(R.layout.rv_reply_item, parent, false) ;
//        ReplyAdapter.ViewHolder vh = new ReplyAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReplyVo replyVo = replyList.get(position);
//        holder.tv_user.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        holder.tv_replyData.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        holder.tv_user.setText(user);
        holder.tv_replyData.setText(replyVo.getReplyData());
        Glide.with(holder.iv_profile.getContext()).load(profile).into(holder.iv_profile);
        Log.d("test",user);
        Log.d("test",replyVo.getReplyData());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (null!= replyList ? replyList.size() : 0);
    }
}
