package com.example.appointed.doctor.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.endpoints.DailyPresetService;
import com.example.appointed.models.DailyPreset;
import com.example.appointed.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DailyPresetAdapter extends RecyclerView.Adapter<DailyPresetAdapter.ViewHolder> {
    Retrofit retrofit;
    List<DailyPreset> dailyPresetList;
    Context context;

    public DailyPresetAdapter(List<DailyPreset> dailyPresetList){
        this.dailyPresetList = dailyPresetList;
    }

    @NonNull
    @Override
    public DailyPresetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_presets,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyPresetAdapter.ViewHolder holder, final int position) {
        final DailyPreset dailyPreset = dailyPresetList.get(position);
        holder.delete_preset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getRootView().getContext());
                alert.setCancelable(true);
                alert.setTitle("Atención");
                alert.setMessage("Al aceptar, usted borrará este preset, desea continuar ?");
                alert.setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final DailyPreset dailyPresetD = dailyPresetList.get(position);
                                retrofit = RetrofitClientInstance.getRetrofitInstance();
                                DailyPresetService aService = retrofit.create(DailyPresetService.class);
                                Call<DailyPreset> call = aService.deletePreset(dailyPresetD.getId());
                                call.enqueue(new Callback<DailyPreset>() {
                                    @Override
                                    public void onResponse(Call<DailyPreset> call, Response<DailyPreset> response) {
                                        if(response.body()!=null){
                                            dailyPresetList.remove(dailyPresetD);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DailyPreset> call, Throwable t) {

                                    }
                                });
                                String itemLabel = String.valueOf(dailyPresetList.get(position));
                                dailyPresetList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,dailyPresetList.size());
                                Toast.makeText(context, "Removed :" + itemLabel, Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        holder.presetName.setText(dailyPreset.getName());
//        holder.add_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddPresetFragment apfr = new AddPresetFragment();
//                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.nav_host_fragment,apfr).addToBackStack(null).commit();
//            }
//        });

//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddPresetFragment apfr = new AddPresetFragment();
//                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.nav_host_fragment,apfr).addToBackStack(null).commit();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return dailyPresetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView delete_preset;
        TextView presetName;
        CardView cv;
        ConstraintLayout constraint;

        public ViewHolder(View itemView)
        {
            super(itemView);
            delete_preset = (ImageView) itemView.findViewById(R.id.delete_preset);
            presetName = (TextView) itemView.findViewById(R.id.presetName);
            cv = (CardView)itemView.findViewById(R.id.add_preset_card);
            constraint = (ConstraintLayout)itemView.findViewById( R.id.constraint);
        }

    }
}

//public class DailyPresetAdapter extends RecyclerView.Adapter {
//    Retrofit retrofit;
//    List<DailyPreset> dailyPresetList;
//    Context context;
//
//    public DailyPresetAdapter(ArrayList<DailyPreset> dailyPresets) {
//        this.dailyPresetList = dailyPresetList;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_presets,parent,false);
//        Vie
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
