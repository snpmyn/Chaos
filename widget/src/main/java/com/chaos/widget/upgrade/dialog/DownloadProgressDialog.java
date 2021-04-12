package com.chaos.widget.upgrade.dialog;

import android.content.Context;

import androidx.appcompat.app.AppCompatDialog;

import com.chaos.widget.R;
import com.chaos.widget.progressbar.MultiProgressBar;

/**
 * @decs: 下载进度对话框
 * @author: 郑少鹏
 * @date: 2018/8/22 8:50
 */
public class DownloadProgressDialog extends AppCompatDialog {
    private final MultiProgressBar multiProgressBar;

    public DownloadProgressDialog(Context context) {
        super(context, R.style.DownloadProgressDialogStyle);
        setContentView(R.layout.download_progress_dialog);
        multiProgressBar = findViewById(R.id.downloadProgressDialogMpb);
        setCancelable(false);
    }

    public void setProgress(long progress, long maxProgress) {
        multiProgressBar.setMax((int) maxProgress);
        multiProgressBar.setProgress((int) progress);
    }

    @Override
    public void show() {
        super.show();
        multiProgressBar.setMax(100);
        multiProgressBar.setProgress(0);
    }
}
