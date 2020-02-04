package kr.co.ldcc.assignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.activity.ImageActivity;
import kr.co.ldcc.assignment.vo.ImageVo;
import kr.co.ldcc.assignment.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private String userId;
    private String profile;
    private ArrayList<ImageVo> imageVos;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewThumbnail;

        ViewHolder(final View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            imageViewThumbnail = itemView.findViewById(R.id.imageViewImageThumbnail);

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
                    final String thumbnail = imageVos.get(pos).getThumbnail_url();
                    final String datetime = imageVos.get(pos).getDatetime();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(v.getContext(), ImageActivity.class);
                        intent.putExtra("thumbnail", thumbnail);
                        intent.putExtra("datetime", datetime);
                        intent.putExtra("userId", userId);
                        intent.putExtra("profile", profile);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ImageAdapter(ArrayList<ImageVo> imageVos, String userId, String profile) {
        this.imageVos = imageVos;
        this.userId = userId;
        this.profile = profile;

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recyclerview_image_item, parent, false);
        ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        final String thumbnail = imageVos.get(position).getThumbnail_url();
        Glide.with(holder.imageViewThumbnail.getContext()).load(thumbnail).into(holder.imageViewThumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return imageVos.size();
    }
}