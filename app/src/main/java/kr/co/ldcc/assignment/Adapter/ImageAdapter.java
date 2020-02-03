package kr.co.ldcc.assignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.Activity.ImageActivity;
import kr.co.ldcc.assignment.Vo.ImageVo;
import kr.co.ldcc.assignment.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    String user;
    String profile;
    private ArrayList<ImageVo> imgList = null ;

    public void setUser(String user) {
        this.user = user;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_thumbnail;

        ViewHolder(final View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            iv_thumbnail = itemView.findViewById(R.id.img_thumbnail);

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
        }


    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ImageAdapter(ArrayList<ImageVo> list,String user,String profile) {
        imgList = list ;
        this.user = user;
        this.profile = profile;

    }

    public ImageAdapter(ArrayList<ImageVo> list){
        imgList = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.rv_img_item, parent, false) ;
        ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        final String thumbnail = imgList.get(position).getThumbnail_url();
        final String datetime = imgList.get(position).getDatetime();
        Glide.with(holder.iv_thumbnail.getContext()).load(thumbnail).into(holder.iv_thumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageActivity.class);
                intent.putExtra("thumbnail", thumbnail);
                intent.putExtra("datetime", datetime);
//                intent.putExtra("user",)
//                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("url",url);
//                intent.putExtra("title",title);
//                intent.putExtra("thumbnail",thumbnail);
                intent.putExtra("user",user);
                intent.putExtra("profile",profile);
                v.getContext().startActivity(intent);
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return imgList.size() ;
    }
    public ArrayList<ImageVo> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<ImageVo> imgList) {
        this.imgList = imgList;
    }

}