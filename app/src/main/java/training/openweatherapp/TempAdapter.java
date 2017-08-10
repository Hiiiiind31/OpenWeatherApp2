package training.openweatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hindahmed on 10/08/17.
 */

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder> {

    private Context context;
    private List<Temps> mitems;


    /* construct a new OrderAdapter.......*/
    public TempAdapter(Context context, List<Temps> mitems) {
        this.context = context;
        this.mitems = mitems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txDay.setText(mitems.get(position).getDay());
        holder.txMax.setText(mitems.get(position).getMax());
        holder.txMin.setText(mitems.get(position).getMin());

    }

    @Override
    public int getItemCount() {
        return mitems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txDay;
        public TextView txMax;
        public TextView txMin;

        public ViewHolder(View itemView) {
            super(itemView);
            txDay = itemView.findViewById(R.id.tDay);
            txMax = itemView.findViewById(R.id.tMax);
            txMin = itemView.findViewById(R.id.tMin);


        }

    }
}
