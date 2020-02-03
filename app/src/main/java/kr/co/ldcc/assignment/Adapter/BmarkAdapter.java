package kr.co.ldcc.assignment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.co.ldcc.assignment.Activity.ImageActivity;
import kr.co.ldcc.assignment.Activity.MainActivity;
import kr.co.ldcc.assignment.Activity.VideoActivity;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.BmarkVo;
import kr.co.ldcc.assignment.Vo.ImageVo;
import kr.co.ldcc.assignment.Vo.VideoVo;

import static android.app.PendingIntent.getActivity;

public class BmarkAdapter extends RecyclerView.Adapter<BmarkAdapter.ViewHolder> {

private ArrayList<BmarkVo> bmarkList = null ;
String userId;
String profile;
// 아이템 뷰를 저장하는 뷰홀더 클래스.
public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tv_title;
    ImageView iv_thumbnail;
    ViewHolder(View itemView) {
        super(itemView) ;
        // 뷰 객체에 대한 참조. (hold strong reference)
        tv_title = itemView.findViewById(R.id.alldata_title) ;
        iv_thumbnail = itemView.findViewById(R.id.alldata_thumbnail);
    }
}

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public BmarkAdapter(ArrayList<BmarkVo> list, String userId, String profile) {
        this.userId = userId;
        this.profile = profile;
        bmarkList = list ;
    }



    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public BmarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.rv_alldata_item, parent, false) ;
        BmarkAdapter.ViewHolder vh = new BmarkAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String title = null;
        String url = bmarkList.get(position).getUrl();
        final String thumbnail = (bmarkList.get(position)).getThumbnail();
        final String datetime = bmarkList.get(position).getDatetime();
        if(bmarkList.get(position).getTitle()==null){
//            thumbnail = (bmarkList.get(position)).getThumbnail();
            holder.tv_title.setText("");
        }else{
            title = (bmarkList.get(position)).getTitle();
//            thumbnail = (bmarkList.get(position)).getThumbnail();
            holder.tv_title.setText(title) ;
        }
        Glide.with(holder.iv_thumbnail.getContext()).load(thumbnail).into(holder.iv_thumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
        final VideoVo videoVo = new VideoVo(title,0,thumbnail,url,datetime,null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if(holder.tv_title.getText().equals("")){
                    intent = new Intent(v.getContext(), ImageActivity.class);
                    intent.putExtra("thumbnail", thumbnail);
                    intent.putExtra("datetime", datetime);
                    intent.putExtra("user",userId);
                    intent.putExtra("profile",profile);
                }else{
                    intent = new Intent(v.getContext(), VideoActivity.class);
                    intent.putExtra("videoVo",videoVo);
                    intent.putExtra("user",userId);
                    intent.putExtra("profile",profile);
                }
                v.getContext().startActivity(intent);
            }
        });
    }


    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return bmarkList.size() ;
    }
    public ArrayList<BmarkVo> getAllDataList() {
        return bmarkList;
    }
    public void setAllDataList(ArrayList<BmarkVo> bmarkList) {
        this.bmarkList = bmarkList;
    }

}