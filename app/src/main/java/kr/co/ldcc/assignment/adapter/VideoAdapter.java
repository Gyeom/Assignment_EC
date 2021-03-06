package kr.co.ldcc.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.activity.VideoActivity;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.vo.VideoVo;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<VideoVo> videoVos;
    private String title;
    private String thumbnail;
    private String userId;
    private String profile;

    public void setVideoVos(ArrayList<VideoVo> videoVos) {
        this.videoVos = videoVos;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView imageViewThumbnail;

        ViewHolder(final View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textViewTitle = itemView.findViewById(R.id.textViewVideoTitle);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewVideoThumbnail);
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP)
                        itemView.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    else
                        itemView.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        VideoVo videoVo = videoVos.get(pos);
                        Intent intent = new Intent(v.getContext(), VideoActivity.class);
                        intent.putExtra("videoVo", videoVo);
                        intent.putExtra("userId", userId);
                        intent.putExtra("profile", profile);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public VideoAdapter(ArrayList<VideoVo> videoVos, String userId, String profile) {
        this.videoVos = videoVos;
        this.userId = userId;
        this.profile = profile;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_video_item, parent, false);
        VideoAdapter.ViewHolder vh = new VideoAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {

        final VideoVo videoVo = videoVos.get(position);
        title = videoVo.getTitle();
        thumbnail = videoVo.getThumbnail();
        Glide.with(holder.imageViewThumbnail.getContext()).load(thumbnail).into(holder.imageViewThumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
        holder.textViewTitle.setText(title);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return (videoVos==null)?0:videoVos.size();
    }
}