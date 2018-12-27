package asus.com.bwie.day1226lx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import asus.com.bwie.day1226lx.bean.ShopBean;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ShopBean.DataBean> list=new ArrayList<>();
    private Context context;

    public ShopAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    public void setList(List<ShopBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<ShopBean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void delList(int position) {
       list.remove(position);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.shopgrid, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ShopBean.DataBean bean = list.get(position);
        String s = bean.getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(s).into(holder.imageView);
        holder.name.setText(bean.getTitle());
        holder.price.setText("￥"+bean.getPrice());
        holder.linear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListenter!=null){
                    longClickListenter.onLongClick(position);
                }
                return true;
            }
        });
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickListenter !=null){
                    ClickListenter.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView name;
        private final TextView price;
        private final LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            linear = itemView.findViewById(R.id.linear);
        }
    }
    public LongClickListenter longClickListenter;

    public void setOnLongClickListenter(LongClickListenter longClickListenter) {
        this.longClickListenter = longClickListenter;
    }

    public interface LongClickListenter{
        void onLongClick(int position);
    }
    public ClickListenter ClickListenter;

    public void setOnClickListenter(ClickListenter ClickListenter) {
        this.ClickListenter = ClickListenter;
    }

    public interface ClickListenter{
        void onClick(int position);
    }


}
