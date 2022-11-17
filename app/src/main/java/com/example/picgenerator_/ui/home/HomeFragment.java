package com.example.picgenerator_.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picgenerator_.R;
import com.example.picgenerator_.databinding.FragmentHomeBinding;

import kotlinx.coroutines.scheduling.Task;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    ArrayList<Task_model> listItems=new ArrayList<Task_model>();
    adapter_task_list list_adapter;
    Spinner spinner;
    ListView taskListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinner = binding.spinner;
        taskListView = binding.taskListView;

        list_adapter = new adapter_task_list(listItems, getActivity());
        taskListView.setAdapter(list_adapter);

        // initialize spinner content

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


        btn_search = binding.btnGenerate;
        et_searchBar = binding.searchBar;

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_model tmp = new Task_model(et_searchBar.getText().toString(), spinner.getSelectedItem().toString(), "Pending");
                listItems.add(tmp);
                list_adapter.notifyDataSetChanged();
//                Intent detail_page = new Intent();
//                detail_page.setClass(getActivity(), Generate.class);
//                detail_page.putExtra("keyword", et_searchBar.getText().toString());
//                detail_page.putExtra("style", homeViewModel.getStyle(spinner.getSelectedItem().toString()));
//                startActivity(detail_page);


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