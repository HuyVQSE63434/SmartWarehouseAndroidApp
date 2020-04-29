package com.example.testloginfb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.pojos.SingleHorizontal;

import java.util.ArrayList;


public class NewsHorizontalAdapter extends RecyclerView.Adapter<NewsHorizontalAdapter.MyViewHolder> {

    public interface onItemClickListener{
        void onItemClick(int position);
        void onItemDelete(int position);
    }
    onItemClickListener mListener;
    ArrayList<SingleHorizontal> data = new ArrayList<>();



    public onItemClickListener getmListener() {
        return mListener;
    }

    public void setmListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(ArrayList<SingleHorizontal> data) {
        this.data = data;
    }
    public ArrayList<SingleHorizontal> getNewsData() {
    return data;
    }
    public static ArrayList<SingleHorizontal> getData() {
        ArrayList<SingleHorizontal> singleHorizontals = new ArrayList<>();
        singleHorizontals.add(new SingleHorizontal(R.mipmap.bg_news, "Quản lý kho có khó không?", "Quản lý kho thời nay cần nhiều thông tin hơn,tiện lợi và thông minh hơn,....", "1/7/2018"));
        singleHorizontals.add(new SingleHorizontal(R.mipmap.bg_caphe, "Nơi hương vị cà phê hoàn hảo", "Không gian và cách chế biến tạo nên đặc trưng hương vị của từng nơi, hãy đến những nơi này để trải nghiệm hương vị tinh tế nhất từ hạt cà phê,....", "10/3/2019"));
        singleHorizontals.add(new SingleHorizontal(R.mipmap.img_double_cup, "Lưu trữ hương vị trong chai thủy tinh", "Sành,sứ,Thủy tinh là chất liệu lưu hương cà phê tốt nhất từ xưa tới nay, cùng nhau đón chào 1 ngày mới với trọn vẹn vị cà phe dù đã pha chế từ lâu", "21/4/2019"));
        return singleHorizontals;
    }

    public static ArrayList<SingleHorizontal> getData2(){
        ArrayList<SingleHorizontal> singleHorizontals = new ArrayList<>();
        singleHorizontals.add(new SingleHorizontal(R.mipmap.bg_doanhthu, "Cửa hàng doanh thu cao nhất quý 1", "Sau cuộc báo cáo công bố vào cuối tháng 3 chúng ta đã tìm ra cửa hàng doanh thu cao nhất quý 1,....", "30/3/2019"));
        singleHorizontals.add(new SingleHorizontal(R.mipmap.bg_award, "Nhân viên ưu tú của tháng 3", "Cuộc bình chọn nhân viên của tháng đã kết thúc, hãy xem ai đã đạt được danh hiệu này.", "6/4/2019"));
        singleHorizontals.add(new SingleHorizontal(R.mipmap.img_tinh_nguyen, "Chương trình tình nguyện tháng 4", "Thông tin chương trình đã được thông báo trên các hệ thống cửa hàng, hãy cùng ban tổ chức tạo điều kiện cho những người khó khăn hơn chúng ta", "1/4/2019"));
        return singleHorizontals;
    }
    public NewsHorizontalAdapter(int i) {
        if(i ==0)
        this.data = getData();
        else  this.data = getData2();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.description.setText(data.get(position).getDesc());
        holder.title.setText(data.get(position).getTitle());
        holder.pubDate.setText(data.get(position).getPubDate());
        holder.image.setImageResource(data.get(position).getImages());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, pubDate;
        ImageView image;

        public MyViewHolder(View itemView ,final onItemClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            description = (TextView) itemView.findViewById(R.id.news_description);
            pubDate = (TextView) itemView.findViewById(R.id.news_published_date);
            image = (ImageView) itemView.findViewById(R.id.news_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        if (getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
