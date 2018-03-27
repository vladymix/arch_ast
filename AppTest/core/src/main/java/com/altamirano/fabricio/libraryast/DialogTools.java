package com.altamirano.fabricio.libraryast;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.altamirano.fabricio.libraryast.controllers.CustomImageDialog;

/**
 * @autor Created by Fabricio Altamirano on 13/3/18.
 */

public class DialogTools  extends DialogFragment {

    private Dialog dialog;
    private String title;
    private String message;

    private boolean contains_image_center = false;
    private Drawable imageCenter;
    private Drawable imageTitle;


    public DialogTools(){
        // need because fragment
        this.title ="";
        this.message = "";
        this.imageCenter = null;
        this.imageTitle = null;
    }

    public DialogTools setImageCenter(Drawable drawable){
        this.imageCenter = drawable;
        return this;
    }

    public DialogTools setImageTitle(Drawable drawable){
        this.imageTitle = drawable;
        return this;
    }

    public DialogTools setTitle(String title){
        this.title = title;
        return this;
    }

    public DialogTools setMessage(String message){
        this.message = message;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.dialog = new CustomImageDialog(this.getActivity());
       ImageView v_imageCenter = dialog.findViewById(R.id.image_center);
       if(this.imageCenter!=null){
           v_imageCenter.setImageDrawable(this.imageCenter);
       }else{
           v_imageCenter.setVisibility(View.GONE);
       }

        ImageView v_imageTitle = dialog.findViewById(R.id.image_title);
        if(this.imageTitle!=null){
            v_imageTitle.setImageDrawable(this.imageTitle);
        }else{
            v_imageTitle.setVisibility(View.GONE);
        }



        return dialog;
    }
}
