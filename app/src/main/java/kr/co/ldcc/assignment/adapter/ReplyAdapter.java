package kr.co.ldcc.assignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.vo.ReplyVo;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    private String userId;
    private String profile;
    private ArrayList<ReplyVo> replyList;

    public void setReplyList(ArrayList<ReplyVo> replyList) {
        this.replyList = replyList;
    }

    public ReplyAdapter(ArrayList<ReplyVo> replyList, String userId, String profile) {
        this.replyList = replyList;
        this.userId = userId;
        this.profile = profile;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProfile;
        private TextView textViewUser;
        private TextView textViewReplyData;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            textViewUser = itemView.findViewById(R.id.textViewUser);
            textViewReplyData = itemView.findViewById(R.id.textViewReplyData);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reply_item, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ReplyVo replyVo = replyList.get(position);
        holder.textViewUser.setText(userId);
        holder.textViewReplyData.setText(replyVo.getReplyData());
        Glide.with(holder.imageViewProfile.getContext()).load(profile).into(holder.imageViewProfile);
    }

    @Override
    public int getItemCount() {
        return (null != replyList ? replyList.size() : 0);
    }
}
