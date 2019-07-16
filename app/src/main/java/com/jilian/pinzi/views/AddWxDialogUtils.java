package com.jilian.pinzi.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jilian.pinzi.R;
import com.jilian.pinzi.dialog.BaseNiceDialog;
import com.jilian.pinzi.dialog.NiceDialog;
import com.jilian.pinzi.dialog.ViewConvertListener;
import com.jilian.pinzi.dialog.ViewHolder;

public class AddWxDialogUtils {

    public static void showAddWxDialog(FragmentActivity activity) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_add_wx)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        dialog.setOutCancel(false);
                        TextView tv_no = (TextView) holder.getView(R.id.tv_no);
                        tv_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                    }
                })
                .setShowBottom(true)
                .show(activity.getSupportFragmentManager());
    }
}
