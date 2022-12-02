package com.example.picgenerator_.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picgenerator_.ui.APICalls.PostTranslation;
import com.example.picgenerator_.ui.Images.ImagesListPage;
import com.example.picgenerator_.R;
import com.example.picgenerator_.databinding.FragmentHomeBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.APICalls.PostTasks;

import org.json.JSONException;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    ArrayList<Task_model> listItems=new ArrayList<Task_model>();
    adapter_task_list list_adapter;
    Spinner spinner;
    ListView taskListView;
    Button btn_search;
    EditText et_searchBar;
    String keyword;
    String style;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btn_search = binding.btnGenerate;
        et_searchBar = binding.searchBar;
        spinner = binding.spinner;
        taskListView = binding.taskListView;

        final PostTasks postTasks = new PostTasks(getActivity());
        final PostTranslation postTranslation = new PostTranslation(getActivity());

        list_adapter = new adapter_task_list(listItems, getActivity());
        taskListView.setAdapter(list_adapter);

        // initialize spinner content

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.style, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = et_searchBar.getText().toString();
                style = spinner.getSelectedItem().toString();
                if (style.equals("Select Style")) {
                    Toast.makeText(getActivity(), "Please choose your style before generating picture.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (keyword.isEmpty()) {
                    Toast.makeText(getActivity(), "Please describe your picture.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Task_model tmp = new Task_model(keyword, style, "Pending", null);
                listItems.add(tmp);
                list_adapter.notifyDataSetChanged();

                try {
                    postTranslation.postTranslate(keyword, new PostTranslation.PostTranslateResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(getActivity(), "PostTranslate: Something Wrong", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response) throws JSONException {
                            postTasks.postTask(response, homeViewModel.getStyle(style), new PostTasks.PostTaskResponseListener(){
                                @Override
                                public void onError(String message) {
                                    Toast.makeText(getActivity(), "PostTask: Something Wrong", Toast.LENGTH_SHORT).show();
                                }
                                // If task successfully created
                                @Override
                                public void onResponse(String[] response) {
                                    String token = response[0];
                                    String taskId = response[1];

                                    Images image = new Images(getActivity());
                                    // Initialize service (thread) to wait on results
                                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();;
                                    // run when task is ready
                                    image.addTaskReadyListener((imgs)-> {
                                        tmp.setTask_status("Ready");
                                        tmp.setImg_urls(imgs);
                                        list_adapter.notifyDataSetChanged();
                                        service.shutdown();
                                    });
                                    // This will run in background to constantly check on status of task. It will trigger listener if task is ready
                                    image.getImages(service, token, taskId);
                                }
                            });
                        }
                    });

                } catch (JSONException e) {}
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task_model task = (Task_model) adapterView.getAdapter().getItem(i);
                if (Objects.equals(task.getTask_status(), "Ready")) {
                    Intent detail_page = new Intent();
                    detail_page.setClass(getActivity(), ImagesListPage.class);
                    detail_page.putStringArrayListExtra("img_urls", new ArrayList<String>(task.getImg_urls()));
//                    System.out.println()
                    detail_page.putExtra("keyword", task.getTask_name());
                    detail_page.putExtra("style", task.getStyle());
                    detail_page.putExtra("ith_request", i);
                    startActivity(detail_page);
                } else {
                    Toast.makeText(getActivity(), "Image generating", Toast.LENGTH_SHORT).show();
                }
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