package com.example.appcanhbaodotquy.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appcanhbaodotquy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatIconManager implements Application.ActivityLifecycleCallbacks {

    private FloatingActionButton chatFab;
    private Application application;

    public ChatIconManager(Application application) {
        this.application = application;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (shouldShowChatIcon(activity)) {
            addChatIconToActivity(activity);
        } else {
            removeChatIconFromActivity(activity);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        removeChatIconFromActivity(activity);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    // chỗ này hay gây ra lỗi chat AI nên Lok comment lại sau có fix bug cho dễ
    private boolean shouldShowChatIcon(Activity activity) {
        return !(activity instanceof MainActivity || activity instanceof Register_Activity || activity instanceof ChatAiActivity || activity instanceof homeActivity);
    }

    private void addChatIconToActivity(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (decorView == null) return;

        View existingFab = decorView.findViewById(R.id.fab_chat);
        if (existingFab != null) {
            setupFabClickListener(existingFab, activity);
            return;
        }

        if (chatFab == null || chatFab.getParent() != null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            chatFab = (FloatingActionButton) inflater.inflate(R.layout.floating_chat_icon, decorView, false);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );

            params.gravity = ((FrameLayout.LayoutParams) chatFab.getLayoutParams()).gravity;
            int margin = (int) activity.getResources().getDimension(R.dimen.fab_margin);
            params.setMargins(margin, margin, margin, margin);
            chatFab.setLayoutParams(params);

            setupFabClickListener(chatFab, activity);
        }

        if (chatFab.getParent() != null) {
            ((ViewGroup) chatFab.getParent()).removeView(chatFab);
        }

        decorView.addView(chatFab);
        chatFab.setVisibility(View.VISIBLE);
    }

    private void removeChatIconFromActivity(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (decorView == null) return;

        View fabToRemove = decorView.findViewById(R.id.fab_chat);
        if (fabToRemove != null) {
            decorView.removeView(fabToRemove);
        }
    }

    private void setupFabClickListener(View fab, final Activity context) {
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatAiActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            if (context instanceof detail_device_Activity) {
                detail_device_Activity detailActivity = (detail_device_Activity) context;
                String initialMessage = detailActivity.getFormattedHealthDataForChat();
                intent.putExtra(ChatAiActivity.EXTRA_INITIAL_MESSAGE, initialMessage);
            }

            context.startActivity(intent);
        });
    }
}