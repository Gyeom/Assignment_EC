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
import kr.co.ldcc.assignment.Activity.VideoActivity;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.BookmarkVo;
import kr.co.ldcc.assignment.Vo.VideoVo;

import static android.app.PendingIntent.getActivity;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private ArrayList<BookmarkVo> bookmarkList;
    private String userId;
    private String profile;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView imageViewThumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            textViewTitle = itemView.findViewById(R.id.imageViewAllDataTitle);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewAllDataThumbnail);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public BookmarkAdapter(ArrayList<BookmarkVo> bookmarkList, String userId, String profile) {
        this.userId = userId;
        this.profile = profile;
        this.bookmarkList = bookmarkList;
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recyclerview_alldata_item, parent, false);
        BookmarkAdapter.ViewHolder vh = new BookmarkAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String title = null;
        String url = bookmarkList.get(position).getUrl();
        final String thumbnail = (bookmarkList.get(position)).getThumbnail();
        final String datetime = bookmarkList.get(position).getDatetime();
        if (bookmarkList.get(position).getTitle() == null) {
//            thumbnail = (bookmarkList.get(position)).getThumbnail();
            holder.textViewTitle.setText("");
        } else {
            title = (bookmarkList.get(position)).getTitle();
//            thumbnail = (bookmarkList.get(position)).getThumbnail();
            holder.textViewTitle.setText(title);
        }
        Glide.with(holder.imageViewThumbnail.getContext()).load(thumbnail).into(holder.imageViewThumbnail); //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
        final VideoVo videoVo = new VideoVo(title, 0, thumbnail, url, datetime, null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (holder.textViewTitle.getText().equals("")) {
                    intent = new Intent(v.getContext(), ImageActivity.class);
                    intent.putExtra("thumbnail", thumbnail);
                    intent.putExtra("datetime", datetime);
                    intent.putExtra("userId", userId);
                    intent.putExtra("profile", profile);
                } else {
                    intent = new Intent(v.getContext(), VideoActivity.class);
                    intent.putExtra("videoVo", videoVo);
                    intent.putExtra("userId", userId);
                    intent.putExtra("profile", profile);
                }
                v.getContext().startActivity(intent);
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

}