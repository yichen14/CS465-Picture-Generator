package com.example.picgenerator_;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.APICalls.PostTasks;

import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Generate extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_imagedetails);
        Intent detail_page_intent = getIntent();

        ImageButton btn_back = findViewById(R.id.btn_back);

        String keyword = detail_page_intent.getStringExtra("keyword");
        String style = detail_page_intent.getStringExtra("style");
        Toast.makeText(Generate.this, "keyword: "+keyword+" style: "+style, Toast.LENGTH_SHORT).show();
        final PostTasks postTasks = new PostTasks(Generate.this);
        try {
            postTasks.postTask(keyword, style, new PostTasks.PostTaskResponseListener(){
                @Override
                public void onError(String message) {
                    Toast.makeText(Generate.this, "PostTask: Something Wrong", Toast.LENGTH_SHORT).show();
                }
                // If task successfully created
                @Override
                public void onResponse(String[] response) {
                    Toast.makeText(Generate.this, "token: "+response[0]+" taskId: "+response[1], Toast.LENGTH_SHORT).show();

                    String token = response[0];
                    String taskId = response[1];

                    Images image = new Images(Generate.this);
                    // Initialize service (thread) to wait on results
                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();;
                    // run when task is ready
                    image.addTaskReadyListener((imgs)-> {
                        System.out.println(imgs);
//                                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, imgs);
//                                images_list.setAdapter(arrayAdapter);
                        System.out.println("ready!!!!!!!!!!!!!!!!");
                        service.shutdown();
                        // after getting image
                        ImageView i1;
                        i1 = findViewById(R.id.generated_image);
                        new Images.DownloadImageTask(i1).execute(imgs.get(0));
                    });
                    // This will run in background to constantly check on status of task. It will trigger listener if task is ready
                    image.getImages(service, token, taskId);

                }
            });
        } catch (JSONException e) {}

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    private void generate_btn_onclick(String keyword)
}