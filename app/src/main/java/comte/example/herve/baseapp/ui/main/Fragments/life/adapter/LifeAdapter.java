package comte.example.herve.baseapp.ui.main.Fragments.life.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.bean.SquareBean;


/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.LifeViewHolder> {


    private Context mContext;

    ArrayList<SquareBean> data = new ArrayList<>();


    public LifeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<SquareBean> data) {
        this.data = data;
    }

    @Override
    public LifeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.template_item_layout, parent, false);

        return new LifeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(LifeViewHolder holder, int position) {
        holder.ivTemplateItem.setImageResource(data.get(position).getResId());
        holder.tvTemplateType.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class LifeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_template_item)
        ImageView ivTemplateItem;
        @BindView(R.id.tv_template_type)
        AppCompatTextView tvTemplateType;

        public LifeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
