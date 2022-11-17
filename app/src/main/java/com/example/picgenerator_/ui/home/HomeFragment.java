package com.example.picgenerator_.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picgenerator_.Generate;
import com.example.picgenerator_.MainActivity;
import com.example.picgenerator_.R;
import com.example.picgenerator_.databinding.FragmentHomeBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.APICalls.PostTasks;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // initialize spinner content
        Spinner spinner = (Spinner) binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.style, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);



        final String AK = "FG0FG1bGAm04DoxduGmp2endv6Xd4Met";
        final String SK = "DTvuzzvGpyaWz63FHLCxooEnXLPMwFLA";
        Button btn_search;
        EditText et_searchBar;
        ListView images_list;


        btn_search = (Button) binding.btnGenerate;
        et_searchBar = binding.searchBar;
//        ImageView i1;
//        images_list = findViewById(R.id.images_list);
//        i1 = findViewById(R.id.i1);
//        String tmp = "https://wenxin.baidu.com/younger/file/ERNIE-ViLG/32fdfb47787777ab6aaaed2fe2799da4i4";
//        new Images.DownloadImageTask(i1).execute(tmp);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail_page = new Intent();
                detail_page.setClass(getActivity(), Generate.class);
                detail_page.putExtra("keyword", et_searchBar.getText().toString());
                detail_page.putExtra("style", homeViewModel.getStyle(spinner.getSelectedItem().toString()));
                startActivity(detail_page);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}