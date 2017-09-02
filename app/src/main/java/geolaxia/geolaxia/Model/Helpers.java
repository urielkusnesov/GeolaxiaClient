package geolaxia.geolaxia.Model;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONException;

import java.lang.reflect.Field;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.RestService;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 15/4/2017.
 */

public class Helpers {
    public static SweetAlertDialog getSuccesDialog(Activity context, String title, String content){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView content = (TextView) alertDialog.findViewById(R.id.content_text);
                content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView confirm = (TextView) alertDialog.findViewById(R.id.confirm_button);
                confirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        });

        return dialog;
    }

    public static SweetAlertDialog getErrorDialog(Activity context, String title, String content){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView content = (TextView) alertDialog.findViewById(R.id.content_text);
                content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView confirm = (TextView) alertDialog.findViewById(R.id.confirm_button);
                confirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        });

        return dialog;
    }

    public static SweetAlertDialog getConfirmationDialog(Activity context, String title, String content, String confirmText, String cancelText){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setCancelText(cancelText)
                .setConfirmText(confirmText)
                .showCancelButton(true);

        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView content = (TextView) alertDialog.findViewById(R.id.content_text);
                content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView confirm = (TextView) alertDialog.findViewById(R.id.confirm_button);
                confirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                TextView cancel = (TextView) alertDialog.findViewById(R.id.cancel_button);
                cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
        });

        return dialog;
    }

    public static SweetAlertDialog getProgessDialog(Activity context, String title){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText(title);
        dialog.setCancelable(false);

        return dialog;
    }

    public static SweetAlertDialog getBasicDialog(Activity context, String title, String content){
        SweetAlertDialog dialog = new SweetAlertDialog(context);
        dialog.setTitleText(title);
        dialog.setContentText(content);

        return dialog;
    }

    /*public static String ObtenerRegistrationTokenEnGcm(Context context) throws  Exception
    {
        InstanceID instanceID = InstanceID.getInstance(context);
        String token = instanceID.getToken("503700765257", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

        return token;
    }

    public static void RegistrarseEnAplicacionServidor(Context context,String registrationToken, String username, String token) throws  Exception
    {
        try {
            IRestService restService = new RestService();
            restService.registerGcmToken(username, token, registrationToken, context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                }
                catch(IllegalAccessException e){
                }
                catch(IllegalArgumentException e){
                }
            }
        }
        return false;
    }
}
