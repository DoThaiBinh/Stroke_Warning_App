package com.example.appcanhbaodotquy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appcanhbaodotquy.R;
import com.example.appcanhbaodotquy.adapter.ChatAdapter;
import com.example.appcanhbaodotquy.Models.ChatMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatAiActivity extends AppCompatActivity {

    private EditText inputText;
    private ImageButton sendButton;
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private String apiKey = "AIzaSyD_rBphXep_AHkFfG2eJTauzXE6gIlW8v8";
    private ArrayList<ChatMessage> chatMessages;
    private RequestQueue queue;

    public static final String EXTRA_INITIAL_MESSAGE = "com.example.appcanhbaodotquy.Activity.EXTRA_INITIAL_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_ai_page);

        inputText = findViewById(R.id.inputText);
        sendButton = findViewById(R.id.sendButton);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        queue = Volley.newRequestQueue(this);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        sendButton.setOnClickListener(v -> {
            String messageText = inputText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                ChatMessage userMessage = new ChatMessage(messageText, true);
                addMessageToList(userMessage);
                inputText.setText("");
                sendRequest(messageText);
            }
        });

        handleInitialMessage();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMessageToList(ChatMessage message) {
        chatMessages.add(message);
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
    }

    private void sendRequest(String input) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        try {
            JSONArray contentsArray = new JSONArray();
            JSONObject userPart = new JSONObject().put("text", input);
            JSONArray userPartsArray = new JSONArray().put(userPart);
            JSONObject userMessageJson = new JSONObject().put("role", "user").put("parts", userPartsArray);
            contentsArray.put(userMessageJson);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("contents", contentsArray);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonBody, response -> {
                        try {
                            JSONArray candidates = response.getJSONArray("candidates");
                            if (candidates.length() > 0) {
                                JSONObject firstCandidate = candidates.getJSONObject(0);
                                String finishReason = firstCandidate.optString("finishReason", "");
                                if ("SAFETY".equalsIgnoreCase(finishReason) || "RECITATION".equalsIgnoreCase(finishReason)) {
                                    ChatMessage safetyMessage = new ChatMessage("Xin lỗi, tôi không thể trả lời câu hỏi này.", false);
                                    addMessageToList(safetyMessage);
                                    return;
                                }

                                JSONObject content = firstCandidate.getJSONObject("content");
                                JSONArray parts = content.getJSONArray("parts");

                                if (parts.length() > 0) {
                                    String answer = parts.getJSONObject(0).getString("text");
                                    ChatMessage aiMessage = new ChatMessage(answer.trim(), false);
                                    addMessageToList(aiMessage);
                                }
                            } else {
                                ChatMessage errorMessage = new ChatMessage("Không nhận được phản hồi hợp lệ.", false);
                                addMessageToList(errorMessage);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ChatMessage errorMessage = new ChatMessage("Lỗi xử lý phản hồi: " + e.getMessage(), false);
                            addMessageToList(errorMessage);
                        }
                    }, error -> {
                        String errorMsg = "Lỗi mạng: ";
                        if (error.networkResponse != null) {
                            errorMsg += "Status " + error.networkResponse.statusCode;
                        } else {
                            errorMsg += error.toString();
                        }
                        ChatMessage errorMessage = new ChatMessage(errorMsg, false);
                        addMessageToList(errorMessage);
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            int initialTimeoutMs = 30000;
            int maxNumRetries = 0;
            float backoffMultiplier = com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

            jsonObjectRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
                    initialTimeoutMs,
                    maxNumRetries,
                    backoffMultiplier));

            jsonObjectRequest.setTag(this);
            queue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
            ChatMessage errorMessage = new ChatMessage("Lỗi tạo request: " + e.getMessage(), false);
            addMessageToList(errorMessage);
        }
    }

    private void handleInitialMessage() {
        Intent intent = getIntent();
        String initialMessageText = "Bị nhồi máu cơ tim thì nên làm gì để ổn hơn?";

        if (intent != null && intent.hasExtra(EXTRA_INITIAL_MESSAGE)) {
            initialMessageText = intent.getStringExtra(EXTRA_INITIAL_MESSAGE);
            Log.d("ChatAiActivity", "Initial message: " + initialMessageText);
        }

        ChatMessage initialUserMessage = new ChatMessage(initialMessageText, true);
        addMessageToList(initialUserMessage);

        if (!initialMessageText.startsWith("Dữ liệu sức khỏe đang được tải")) {
            sendRequest(initialMessageText);
        } else {
            ChatMessage waitingMessage = new ChatMessage("Đang tải dữ liệu sức khỏe, vui lòng chờ một chút...", false);
            addMessageToList(waitingMessage);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(this);
        }
    }
}