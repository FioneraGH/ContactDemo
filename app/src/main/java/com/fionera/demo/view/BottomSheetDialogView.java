package com.fionera.demo.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.fionera.demo.R;

/**
 * Created by fionera on 16-3-1.
 */
public class BottomSheetDialogView
        implements View.OnClickListener {

    private TextView tvDialNumber;
    private Context context;

    private BottomSheetDialogView(Context context,
                                  DialogInterface.OnDismissListener onDismissListener) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        View view = View.inflate(context, R.layout.bottom_sheet_dialog_dial, null);

        this.context = context;
        GridLayout gridLayout = (GridLayout) view.findViewById(R.id.bottom_sheet_grid_view);
        tvDialNumber = (TextView) view.findViewById(R.id.tv_dial_number);
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            gridLayout.getChildAt(i).setTag(i);
            gridLayout.getChildAt(i).setOnClickListener(this);
        }

        dialog.setContentView(view);
        dialog.setOnDismissListener(onDismissListener);
        dialog.show();
    }

    public static void show(Context context, DialogInterface.OnDismissListener onDismissListener) {
        new BottomSheetDialogView(context, onDismissListener);
    }

    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();
        if (tvDialNumber != null) {
            switch (pos) {
                case 0:
                    tvDialNumber.append("1");
                    break;
                case 1:
                    tvDialNumber.append("2");
                    break;
                case 2:
                    tvDialNumber.append("3");
                    break;
                case 3:
                    tvDialNumber.append("4");
                    break;
                case 4:
                    tvDialNumber.append("5");
                    break;
                case 5:
                    tvDialNumber.append("6");
                    break;
                case 6:
                    tvDialNumber.append("7");
                    break;
                case 7:
                    tvDialNumber.append("8");
                    break;
                case 8:
                    tvDialNumber.append("9");
                    break;
                case 10:
                    tvDialNumber.append("0");
                    break;
                case 9:
                    tvDialNumber.setText("");
                    break;
                case 11:
                    String phone = tvDialNumber.getText().toString();
                    if(TextUtils.isEmpty(phone)){
                        return;
                    }
                    Uri uri = Uri.parse("tel:" + tvDialNumber.getText());
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}