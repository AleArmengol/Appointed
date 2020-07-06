package com.example.appointed.doctor.ui_doctor.my_presets;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointed.R;
import com.example.appointed.doctor.adapters.DailyPresetAdapter;
import com.example.appointed.doctor.ui_doctor.add_preset.AddPresetFragment;
import com.example.appointed.endpoints.DailyPresetService;
import com.example.appointed.models.DailyPreset;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPresetFragment extends Fragment {
    private View rootView;
    private ImageButton add_button;
    private ConstraintLayout addPreset;
    private RecyclerView recyclerView;
    private DailyPresetAdapter dailyPresetAdapter;
    private ArrayList<DailyPreset> dailyPresets;
    private MyPresetViewModel mViewModel;

    public static MyPresetFragment newInstance() {
        return new MyPresetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_preset_fragment,container,false);
        add_button = (ImageButton) rootView.findViewById(R.id.add_button);
        addPreset = (ConstraintLayout) rootView.findViewById(R.id.addPreset);
        dailyPresets = new ArrayList<>();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPresetFragment apfr = new AddPresetFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.doctor_nav_host_fragment,apfr).addToBackStack(null).commit();
            }
        });

        addPreset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                AddPresetFragment apfr = new AddPresetFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.doctor_nav_host_fragment,apfr).addToBackStack(null).commit();
            }
        });

        this.getPresets();
        return rootView;
    }

    private void getPresets() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/").addConverterFactory(GsonConverterFactory.create()).build();
        DailyPresetService pService = retrofit.create(DailyPresetService.class);
        Call<List<DailyPreset>> call = pService.getPresets(2);

        call.enqueue(new Callback<List<DailyPreset>>() {
            @Override
            public void onResponse(Call<List<DailyPreset>> call, Response<List<DailyPreset>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(response.body() != null){
                    for(DailyPreset post : response.body()){
                        dailyPresets.add(post);
                    }
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.daily_presetRV);
                    dailyPresetAdapter = new DailyPresetAdapter(dailyPresets);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(dailyPresetAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<DailyPreset>> call, Throwable t) {
                Log.d("ERROR", "ERROR ONQUEUE");

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyPresetViewModel.class);
        // TODO: Use the ViewModel
    }

}
