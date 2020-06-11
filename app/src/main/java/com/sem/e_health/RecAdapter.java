package com.sem.e_health;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {
    private List<Test> list;
    Context Mycontext ;

    public RecAdapter(Context mycontext,List<Test> list) {
        this.list = list;
       Mycontext = mycontext;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder,int position) {
         Test test = list.get(position );

        holder.bind(test);
        holder.itemView.setOnClickListener(v ->{
                boolean expanded = test.isExpanded();
                    test.setExpanded(!expanded);
                    notifyItemChanged(position);
        });
    }
    public void removeItem(int position, DatabaseReference testRef) {
        list.remove(position +1);
        Test test = list.get((position));
        String t = test.getTime() ;
        testRef.child(t).removeValue();
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView temp;
        private TextView hb;
        private TextView emg;
        private TextView gluc;
        private View subItem;

        public RecViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.item_date);
            temp = itemView.findViewById(R.id.sub_item_temp);
            hb = itemView.findViewById(R.id.sub_item_hb);
            subItem = itemView.findViewById(R.id.sub_item);
            emg= itemView.findViewById(R.id.sub_item_emg);
            gluc = itemView.findViewById(R.id.sub_item_gluc);

        }



        private void bind(Test test) {
            boolean expanded = test.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            date.setText(test.getTime());
            temp.setText("Temp: " + test.getTemp());
            hb.setText("Hart Beats: " + test.getHartbeats());
            emg.setText("EMG: "+test.getEmg());
            gluc.setText("Glucose: "+test.getGlucose()+" mg/dL");
        }
    }
}
