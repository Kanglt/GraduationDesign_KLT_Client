/**     
*/
package lyu.klt.graduationdesign.module.adapter;



import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
     
     private List<String> mDatas;
     private Context mContext;
     private LayoutInflater inflater;
     
     public MyRecyclerAdapter(Context context, List<String> datas){
            this. mContext=context;
            this. mDatas=datas;
            inflater=LayoutInflater. from(mContext);
     }
     
     @Override
     public int getItemCount() {
           
            return mDatas.size();
     }

     //填充onCreateViewHolder方法返回的holder中的控件
     @Override
     public void onBindViewHolder(MyViewHolder holder, final int position) {
           
          // holder.tv.setText( mDatas.get(position));
     }

     //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
     @Override
     public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           
           View view = inflater.inflate(R.layout. listview_list_item_fitness,parent, false);
           MyViewHolder holder= new MyViewHolder(view);
            return holder;
     }
     
     class MyViewHolder extends ViewHolder{
           
           TextView tv;

            public MyViewHolder(View view) {
                 super(view);
              //   tv=(TextView) view.findViewById(R.id. tv_item);
           }
           
     }
}
