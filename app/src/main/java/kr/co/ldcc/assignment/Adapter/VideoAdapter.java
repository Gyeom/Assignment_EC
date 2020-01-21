package kr.co.ldcc.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.VideoVo;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ArrayList<VideoVo> vdList = null ;
    
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_thumbnail;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            tv_title = itemView.findViewById(R.id.vd_title) ;
            iv_thumbnail = itemView.findViewById(R.id.vd_thumbnail);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public VideoAdapter(ArrayList<VideoVo> list) {
        vdList = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.rv_vd_item, parent, false) ;
        VideoAdapter.ViewHolder vh = new VideoAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        String title = vdList.get(position).getTitle() ;
        String thumbnail = vdList.get(position).getThumbnail();
        Glide.with(holder.iv_thumbnail.getContext()).load(thumbnail).into(holder.iv_thumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
        holder.tv_title.setText(title) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return vdList.size() ;
    }
    public ArrayList<VideoVo> getmData() {
        return vdList;
    }

    public void setmData(ArrayList<VideoVo> mData) {
        this.vdList = mData;
    }

}