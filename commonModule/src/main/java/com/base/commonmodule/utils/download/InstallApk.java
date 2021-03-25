package com.base.commonmodule.utils.download;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Created by byz on 2018/12/3.
 */
public class InstallApk {

    Context context;

    public InstallApk(Context context) {
        this.context = context;
    }

    public void installApk(File apkFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                installApkLevel26(apkFile, intent);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                            context.getApplicationInfo().packageName + ".fileProvider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    context.startActivity(intent);
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {

        }



    }

    public Intent getInstallIntent(File apkFile) {
        if (!apkFile.exists())return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                    context.getApplicationInfo().packageName + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        context.getApplicationInfo().packageName + ".fileProvider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        return intent;
    }


    @TargetApi(26)
    private void installApkLevel26(File apkFile, Intent intent) {
        boolean b = context.getPackageManager().canRequestPackageInstalls();
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                context.getApplicationInfo().packageName + ".fileProvider", apkFile);
        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
//        if (b) {
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
//                        BuildConfig.APPLICATION_ID+".fileProvider", apkFile);
//                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//                context.startActivity(intent);
//            } else {
//                //请求安装未知应用来源的权限
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Android8.0安装应用需要打开未\n知来源权限，请去设置中开启权限");
//                builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        String[] mPermissionList = new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES};
////                        ActivityCompat.requestPermissions(context, mPermissionList, 2);
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + context.getPackageName()));
//                        context.startActivityForResult(intent, 1);
//
//                    }
//                });
//                builder.setNegativeButton("取消", null);
//                builder.show();
//            }
    }

}
