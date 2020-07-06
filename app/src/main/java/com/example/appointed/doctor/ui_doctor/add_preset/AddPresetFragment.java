package com.example.appointed.doctor.ui_doctor.add_preset;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;
import com.example.appointed.doctor.adapters.AddDailyPresetAdapter;
import com.example.appointed.endpoints.DailyPresetService;
import com.example.appointed.endpoints.SpecialityService;
import com.example.appointed.models.Speciality;
import com.example.appointed.models.TimeFrame;
import com.example.appointed.posts.PresetPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPresetFragment extends Fragment {

    private View rootView;
    private AddPresetViewModel mViewModel;
    private Spinner speciality_spinner;
    private ConstraintLayout addPreset;
    private ImageButton add_button;
    private FloatingActionButton floating_save_button;
    private Speciality specialitySelected;
    private ConstraintLayout add;
    private ImageButton add_btn;
    private TextView label_name;

    private ListView timeFrameListView;
    private AddDailyPresetAdapter addDailyPresetAdapter;
    //    private Doctor loggedDoctor;
    private EditText name;
    private List<TimeFrame> time_frames;
    private List<TimeFrame> toSend = new ArrayList<>();






    ArrayAdapter<String> doctorSpecialityAdapter;
    ArrayList<String> specialities_names = new ArrayList<>();
    ArrayList<Speciality> specialities = new ArrayList<>();



    public static AddPresetFragment newInstance() {
        return new AddPresetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_preset_fragment,container,false);

        doctorSpecialityAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner_item, specialities_names);
        speciality_spinner = (Spinner) rootView.findViewById(R.id.speciality_spinner);
        name = (EditText) rootView.findViewById(R.id.name);
        add = (ConstraintLayout) rootView.findViewById(R.id.add);
        add_btn = (ImageButton) rootView.findViewById(R.id.add_btn);
        label_name = (TextView) rootView.findViewById(R.id.label_name);
        floating_save_button = (FloatingActionButton) rootView.findViewById(R.id.floating_save_button);

        timeFrameListView = (ListView) rootView.findViewById(R.id.timeFrameListView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        time_frames = new ArrayList<>();

        specialities_names.add("Seleccione una especialidad ...");
        addDailyPresetAdapter = new AddDailyPresetAdapter(getActivity(), time_frames); //chequear esta manera
        timeFrameListView.setAdapter(addDailyPresetAdapter);

        addItemsOnSpecialitySpinner();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeFrame timeFrame = new TimeFrame();
//                timeFrame.setStart_time("00:00");
//                timeFrame.setEnd_time("07:00");
                time_frames.add(timeFrame);

                addDailyPresetAdapter.notifyDataSetChanged();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeFrame timeFrame = new TimeFrame();
//                timeFrame.setStart_time("00:00");
//                timeFrame.setEnd_time("07:00");
                time_frames.add(timeFrame);

                addDailyPresetAdapter.notifyDataSetChanged();

            }
        });

        floating_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cards_number = timeFrameListView.getChildCount();
                for(int i = 0; i < cards_number; i++){
                    View tfView = timeFrameListView.getChildAt(i);
                    EditText edit_start1 = tfView.findViewById(R.id.edit_start1);
                    EditText edit_start2 = tfView.findViewById(R.id.edit_start2);
                    EditText edit_end1 = tfView.findViewById(R.id.edit_end1);
                    EditText edit_end2 = tfView.findViewById(R.id.edit_end2);
                    String startTime = edit_start1.getText().toString() + ":" + edit_start2.getText().toString(); // agregar los otros 2 faltantes
                    String endTime = edit_end1.getText().toString()  + ":" + edit_end2.getText().toString();
                    TimeFrame timeFrameToSend = new TimeFrame();
                    timeFrameToSend.setStart_time(startTime);
                    timeFrameToSend.setEnd_time(endTime);
                    toSend.add(timeFrameToSend);
                }
                createPreset();
            }
        });



        speciality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    specialitySelected = specialities.get(i - 1);
                }
                else{
                    specialitySelected = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    private void createPreset() {
        addDailyPresetAdapter.notifyDataSetChanged();
        PresetPost post = new PresetPost();
        post.setName(name.getText().toString());
        post.setSpeciality_id(specialitySelected.getId());
        post.setDoctor_id(2);
        post.setTime_frames(toSend);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DailyPresetService dpService = retrofit.create(DailyPresetService.class);
        Call<PresetPost> call = dpService.createPreset(post);

        call.enqueue(new Callback<PresetPost>() {
            @Override
            public void onResponse(Call<PresetPost> call, Response<PresetPost> response) {
                Log.d("RESULTADO:", "onResponse: SE CARGO BIEN");
            }

            @Override
            public void onFailure(Call<PresetPost> call, Throwable t) {
                Log.d("RESULTADO:", "onFailure: FALLO");
            }
        });


    }

    private void addItemsOnSpecialitySpinner() {
        specialities_names.clear();
        specialities.clear();
        specialities_names.add("Seleccione una especialidad ...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SpecialityService sService = retrofit.create(SpecialityService.class);
        Call<List<Speciality>> call = sService.getResponse(2);

        call.enqueue(new Callback<List<Speciality>>() {
            @Override
            public void onResponse(Call<List<Speciality>> call, Response<List<Speciality>> response) {
                if(response.body() != null){
                    for(Speciality s : response.body()){
                        specialities_names.add(s.getName());
                        specialities.add(s);
                    }
                    doctorSpecialityAdapter.setDropDownViewResource(R.layout.spinner_item);
                    speciality_spinner.setAdapter(doctorSpecialityAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Speciality>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPresetViewModel.class);
        // TODO: Use the ViewModel
    }

}
