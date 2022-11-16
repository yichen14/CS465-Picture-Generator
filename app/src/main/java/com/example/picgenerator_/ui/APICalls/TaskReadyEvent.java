package com.example.picgenerator_.ui.APICalls;

import java.util.List;

public interface TaskReadyEvent {
    void onTaskReady(List<String> imgUrls);
}