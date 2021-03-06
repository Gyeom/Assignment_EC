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

import kr.co.ldcc.assignment.activity.ImageActivity;
import kr.co.ldcc.assignment.activity.VideoActivity;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.vo.ImageVo;
import kr.co.ldcc.assignment.vo.VideoVo;

public class AllDataAdapter extends RecyclerView.Adapter<AllDataAdapter.ViewHolder> {

    private ArrayList<Object> allDataVos;
    private String userId;
    private String profile;

    public void setAllDataVos(ArrayList<Object> allDataVos) {
        this.allDataVos = allDataVos;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView imageViewThumbnail;

        ViewHolder(final View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textViewTitle = itemView.findViewById(R.id.imageViewAllDataTitle);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewAllDataThumbnail);

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
                    int position = getAdapterPosition();
                    Object item = allDataVos.get(position);
                    Intent intent=null;
                    if(item instanceof ImageVo){
                        final String thumbnail = ((ImageVo) item).getThumbnail_url();
                        final String datetime = ((ImageVo) item).getDatetime();
                        intent = new Intent(v.getContext(), ImageActivity.class);
                        intent.putExtra("thumbnail", thumbnail);
                        intent.putExtra("datetime", datetime);
                        intent.putExtra("userId", userId);
                        intent.putExtra("profile", profile);
                    }else if(item instanceof VideoVo){
                        intent = new Intent(v.getContext(), VideoActivity.class);
                        intent.putExtra("videoVo", (VideoVo)item);
                        intent.putExtra("userId", userId);
                        intent.putExtra("profile", profile);
                    }
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public AllDataAdapter(ArrayList<Object> allDataVos, String userId, String profile) {
        this.allDataVos = allDataVos;
        this.userId = userId;
        this.profile = profile;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public AllDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_alldata_item, parent, false);
        AllDataAdapter.ViewHolder vh = new AllDataAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final AllDataAdapter.ViewHolder holder, final int position) {
        if (allDataVos.get(position) instanceof ImageVo) {
            final String thumbnail = ((ImageVo) allDataVos.get(position)).getThumbnail_url();
            Glide.with(holder.imageViewThumbnail.getContext()).load(thumbnail).into(holder.imageViewThumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
            holder.textViewTitle.setText("");
        } else if (allDataVos.get(position) instanceof VideoVo) {
            final String title = ((VideoVo) allDataVos.get(position)).getTitle();
            final VideoVo videoVo = ((VideoVo) allDataVos.get(position));
            String thumbnail = videoVo.getThumbnail();
            Glide.with(holder.imageViewThumbnail.getContext()).load(thumbnail).into(holder.imageViewThumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
            holder.textViewTitle.setText(title);
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return allDataVos==null?0:allDataVos.size();
    }
}