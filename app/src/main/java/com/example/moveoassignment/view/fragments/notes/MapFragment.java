package com.example.moveoassignment.view.fragments.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moveoassignment.R;
import com.example.moveoassignment.databinding.FragmentMapBinding;
import com.example.moveoassignment.model.Note;
import com.example.moveoassignment.view.activities.NotesActivity;
import com.example.moveoassignment.viewmodel.NotesViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapFragment extends Fragment {
    private NotesViewModel mNotesViewModel;
    private FragmentMapBinding mFragmentMapBinding;
    private Map<String, Note> notesMap;
    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.clear();
            if (notesMap != null) {
                for (Note note : notesMap.values()) {
                    LatLng coordinates = new LatLng(note.getLocation().getLatitude(), note.getLocation().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(coordinates).title(note.getNoteID()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f));
                    googleMap.setOnMarkerClickListener(marker -> {
                        if (getActivity() != null) {
                            ((NotesActivity) getActivity()).showNewTaskDialog(notesMap.get(marker.getTitle()));
                            return true;
                        }
                        return false;
                    });
                }
            }
            mFragmentMapBinding.map.onResume();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false);
        return mFragmentMapBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentMapBinding.map.onCreate(savedInstanceState);
        mNotesViewModel = ((NotesActivity) getActivity()).getNotesViewModel();
        mNotesViewModel.getIsDataChanged().observe(getViewLifecycleOwner(), hasData ->
        {
            if (hasData) {
                notesMap = mNotesViewModel.getNotesMap().getValue();
                mFragmentMapBinding.map.getMapAsync(callback);
            }
        });
    }
}