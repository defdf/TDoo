package df.tdoo.Utilities;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DialogHelper {

    public void createOkayAlert(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false);

        builder.setPositiveButton(
                "OK",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void createYesNoAlert(String message,
                                 Context context,
                                 DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Yes", onClickListener)
                .setNegativeButton("No",onClickListener)
                .show();
    }

}
