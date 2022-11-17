package com.example.picgenerator_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.picgenerator_.databinding.FragmentHomeBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.APICalls.PostTasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.picgenerator_.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;




public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button btn_search;
    private EditText et_searchBar;
    private ListView images_list;
    private FragmentHomeBinding binding1;
    private ImageButton button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_gallery)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        button1 = (ImageButton) findViewById(R.id.imagebutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),BackMain.class);
                startActivity(intent);}
            });



//        btn_search = findViewById(R.id.nav_host_fragment_activity_main);
//        et_searchBar = binding1.searchBar;
//        final PostTasks accessToken = new PostTasks(android.content.Context.getContext());
        Button button0 = (Button)findViewById(R.id.btn_generate);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),Generate.class);
                startActivity(intent);
//                try {
//                    accessToken.postTask(et_searchBar.getText().toString(), "low poly", new PostTasks.PostTaskResponseListener(){
//                        @Override
//                        public void onError(String message) {
//                            Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
//                        }
//                        // If task successfully created
//                        @Override
//                        public void onResponse(String[] response) {
//                            Toast.makeText(getContext(), "token: "+response[0]+" taskId: "+response[1], Toast.LENGTH_SHORT).show();
//
//                            String token = response[0];
//                            String taskId = response[1];
//
//                            Images image = new Images(getContext());
//                            // Initialize service (thread) to wait on results
//                            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();;
//                            // run when task is ready
//                            image.addTaskReadyListener((imgs)-> {
//                                System.out.println(imgs);
//                                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, imgs);
//                                images_list.setAdapter(arrayAdapter);
//                                System.out.println("ready!!!!!!!!!!!!!!!!");
//                                service.shutdown();
//                            });
//                            // This will run in background to constantly check on status of task. It will trigger listener if task is ready
//                            image.getImages(service, token, taskId);
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });


    }


}