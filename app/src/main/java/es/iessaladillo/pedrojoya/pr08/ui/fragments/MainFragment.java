package es.iessaladillo.pedrojoya.pr08.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import es.iessaladillo.pedrojoya.pr08.R;

public class MainFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private FloatingActionButton fabDetails;
    private SharedPreferences settings;
    private TextView textLorem;
    private NavController navController;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        textLorem = ViewCompat.requireViewById(getView(), R.id.textLorem);
        setupFab(requireView());
        setupToolbar(requireView());
        settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        showLoremText();
    }

    @Override
    public void onResume() {
        super.onResume();
        settings.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        settings.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }


    private void setupFab(View view) {
        fabDetails = ViewCompat.requireViewById(view, R.id.details_fab);
        fabDetails.setOnClickListener(this::navigateDetails);
    }


    private void setupToolbar(View view) {
        Toolbar toolbar = ViewCompat.requireViewById(view, R.id.toolbar);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.main_toolbarFragment));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void navigateDetails(View v) {
        Navigation.findNavController(v).navigate(R.id.action_mainFragmentToDetailsFragment);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.pref_listKey))) { showLoremText(); }
    }

    private void showLoremText() {
        textLorem.setText(TextUtils.equals(settings.getString(getString(R.string.pref_listKey), getString(R.string.pref_listDefaultValue)), "latin") ? getString(R.string.main_latin_ipsum) : getString(R.string.main_chiquito_ipsum));
    }
}
