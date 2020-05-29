package com.example.appointed.ui.cancelled_appointments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appointed.models.Appointment;
import com.example.appointed.endpoints.AppointmentService;
import com.example.appointed.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancelledAppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelledAppointmentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ListView listCancelled;
    ArrayList<String> cancelled_appointments = new ArrayList<>();
    ArrayAdapter<String> adapter;
    TextView message;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CancelledAppointmentsFragment() {
        Log.d("TEST", "EMPTY CONSTR");
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelledAppointmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelledAppointmentsFragment newInstance(String param1, String param2) {
        CancelledAppointmentsFragment fragment = new CancelledAppointmentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TEST", "ON CREATE CAF");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TEST", "ON CREATE VIEW CAF");
        // Inflate the layout for this fragment
        listCancelled = (ListView)inflater.inflate(R.layout.fragment_cancelled_appointments, container, false).findViewById(R.id.listCancelled);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.fragment_cancelled_appointments, cancelled_appointments);
        message = (TextView) inflater.inflate(R.layout.fragment_cancelled_appointments, container, false).findViewById(R.id.messageText);
        listCancelled.setAdapter(adapter);
        this.getAppointments();
        return inflater.inflate(R.layout.fragment_cancelled_appointments, container, false);
    }

    private void getAppointments() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppointmentService aService = retrofit.create(AppointmentService.class);
        Call<List<Appointment>> call = aService.getAppointments();

        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                int x = 0;
                if (response.body() != null){
                    for (Appointment post : response.body()){
                        cancelled_appointments.add(String.valueOf(post.getDoctor_name()));
                        cancelled_appointments.add(String.valueOf(post.getStart_time()));
                        cancelled_appointments.add(String.valueOf(post.getEnd_time()));
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    message.setText("No trajo nada");
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                message.setText(t.getMessage());
            }
        });
    }
}
