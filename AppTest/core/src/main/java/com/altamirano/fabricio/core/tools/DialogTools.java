package com.altamirano.fabricio.core.tools;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.altamirano.fabricio.core.R;
import com.altamirano.fabricio.core.Utils.A;
import com.altamirano.fabricio.core.commons.BaseDialog;

/**
 * @autor Created by Fabricio Altamirano on 13/3/18.
 */

public class DialogTools extends DialogFragment {

    private String btn_primary;
    private String btn_secundary;
    private boolean contains_image_center = false;
    private Dialog dialog;
    private Drawable imageCenter;
    private Drawable imageTitle;
    private String message;
    private View.OnClickListener onClickPrimay = null;
    private View.OnClickListener onClickSecundary = null;
    private String title;

    public DialogTools() {
        // need because fragment
        this.title = A.StrEmpty;
        this.message = A.StrEmpty;
        this.imageCenter = null;
        this.imageTitle = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.dialog = new BaseDialog(this.getActivity());
        ImageView v_imageCenter = dialog.findViewById(R.id.image_center);
        TextView mTitle = dialog.findViewById(R.id.dg_tittle);
        TextView mMessage = dialog.findViewById(R.id.dg_message);
        Button mSecundary = dialog.findViewById(R.id.dg_btn_secundary);
        Button mprimary = dialog.findViewById(R.id.dg_btn_primary);

        mprimary.setOnClickListener(this.onClickPrimay);
        mprimary.setText(this.btn_primary);

        mSecundary.setOnClickListener(this.onClickSecundary);
        mSecundary.setText(this.btn_secundary);

        mTitle.setText(A.StrEmpty);
        mMessage.setText(A.StrEmpty);

        if (this.title != null) {
            mTitle.setText(this.title);
        }

        if (this.message != null) {
            mMessage.setText(this.message);
        }

        if (this.imageCenter != null) {
            v_imageCenter.setImageDrawable(this.imageCenter);
        } else {
            v_imageCenter.setVisibility(View.GONE);
        }

        ImageView v_imageTitle = dialog.findViewById(R.id.image_title);
        if (this.imageTitle != null) {
            v_imageTitle.setImageDrawable(this.imageTitle);
        } else {
            v_imageTitle.setVisibility(View.GONE);
        }


        return dialog;
    }

    public DialogTools setImageCenter(Drawable drawable) {
        this.imageCenter = drawable;
        return this;
    }

    public DialogTools setImageTitle(Drawable drawable) {
        this.imageTitle = drawable;
        return this;
    }

    public DialogTools setMessage(String message) {
        this.message = message;
        return this;
    }

    public DialogTools setPrimaryButton(String value, View.OnClickListener onClick) {
        this.btn_primary = value;
        this.onClickPrimay = onClick;
        return this;
    }

    public DialogTools setSecundaryButton(String value, View.OnClickListener onClick) {
        this.btn_secundary = value;
        this.onClickSecundary = onClick;
        return this;
    }

    public DialogTools setTitle(String title) {
        this.title = title;
        return this;
    }

}
