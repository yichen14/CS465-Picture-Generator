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

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

//        Spinner spinner = (Spinner) binding.spinner;
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(homeViewModel,
//                R.array.style, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);



        final String AK = "FG0FG1bGAm04DoxduGmp2endv6Xd4Met";
        final String SK = "DTvuzzvGpyaWz63FHLCxooEnXLPMwFLA";
        Button btn_search;
        EditText et_searchBar;
        ListView images_list;
//    ImageView i1;

//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        btn_search = (Button) binding.btnGenerate;
        et_searchBar = binding.searchBar;
//        images_list = findViewById(R.id.images_list);
//        i1 = findViewById(R.id.i1);
//        String tmp = "https://wenxin.baidu.com/younger/file/ERNIE-ViLG/32fdfb47787777ab6aaaed2fe2799da4i4";
//        new Images.DownloadImageTask(i1).execute(tmp);

        final PostTasks accessToken = new PostTasks(getActivity());

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    accessToken.postTask(et_searchBar.getText().toString(), "low poly", new PostTasks.PostTaskResponseListener(){
                        @Override
                        public void onError(String message) {
                            Toast.makeText(getActivity(), "PostTask: Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                        // If task successfully created
                        @Override
                        public void onResponse(String[] response) {
                            Toast.makeText(getActivity(), "token: "+response[0]+" taskId: "+response[1], Toast.LENGTH_SHORT).show();

                            String token = response[0];
                            String taskId = response[1];

                            Images image = new Images(getActivity());
                            // Initialize service (thread) to wait on results
                            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();;
                            // run when task is ready
                            image.addTaskReadyListener((imgs)-> {
                                System.out.println(imgs);
//                                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, imgs);
//                                images_list.setAdapter(arrayAdapter);
                                System.out.println("ready!!!!!!!!!!!!!!!!");
                                service.shutdown();
                            });
                            // This will run in background to constantly check on status of task. It will trigger listener if task is ready
                            image.getImages(service, token, taskId);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}