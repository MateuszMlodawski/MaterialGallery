/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.toolbox;

import android.app.Dialog;
import android.content.Context;

import com.mmlodawski.materialgallery.R;

public class DialogHelper {

    /**
     * Creates and shows custom Dialog
     * @param c Context
     * @return Created Dialog
     */
    public static Dialog createProgressDialogAndShow(Context c) {
        Dialog dialog = new Dialog(c, R.style.CustomDialogTheme);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_progress);

        dialog.show();
        return dialog;
    }
}
