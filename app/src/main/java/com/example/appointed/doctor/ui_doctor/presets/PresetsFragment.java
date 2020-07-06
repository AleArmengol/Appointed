package com.example.appointed.doctor.ui_doctor.presets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appointed.R;
import com.example.appointed.doctor.ui_doctor.my_presets.MyPresetFragment;


public class PresetsFragment extends Fragment {
    private ImageButton daily_presets_button;
    private PresetsViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(PresetsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_presets, container, false);
        daily_presets_button = (ImageButton) root.findViewById(R.id.daily_presets_button);


        daily_presets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPresetFragment mpfr = new MyPresetFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment,mpfr).addToBackStack(null).commit();
            }
        });

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}
