package com.whiteguru.capacitor.plugin.fileopener;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.core.content.FileProvider;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.File;

@CapacitorPlugin(name = "FileOpener")
public class FileOpenerPlugin extends Plugin {

    @PluginMethod
    public void open(PluginCall call) {
        String path = call.getString("path", "");

        File file;
        Uri u = Uri.parse(path);
        if (u.getScheme() == null || u.getScheme().equals("file")) {
            file = new File(u.getPath());
        } else {
            file = new File(path);
        }

        Uri fileUri = FileProvider.getUriForFile(getActivity(), getContext().getPackageName() + ".fileprovider", file);

        Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
        openFileIntent.setDataAndNormalize(fileUri);
        openFileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (openFileIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(call, openFileIntent, "openFileResult");
        } else {
            call.reject("No default apps for open file");
        }
    }

    @ActivityCallback
    private void openFileResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }

        call.resolve();
    }
}
