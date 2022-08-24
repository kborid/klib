package com.kborid.common.helper;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;

import com.kborid.common.ThunisoftCommon;

/**
 * @description: 系统画面跳转类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class SystemScreenChangeHelper {
    /**
     * 打开系统无线和网络设置画面
     */
    public static void startNetworkSettingScreen() {
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        ThunisoftCommon.getContext().startActivity(intent);
    }

    /**
     * 打开拨号画面
     *
     * @param phone 电话
     */
    public static void startDialScreen(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            ThunisoftCommon.getContext().startActivity(intent);
        }
    }

    /**
     * 打开联系人编辑画面
     *
     * @param name  姓名
     * @param phone 电话
     */
    public static void startContactEditScreen(String name, String company, String phone) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setDataAndType(Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"), "vnd.android.cursor.dir/person");
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
            ThunisoftCommon.getContext().startActivity(intent);
        }
    }
}
