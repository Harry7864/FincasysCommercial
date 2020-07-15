package com.example.fincasyscommercial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class Tools {


     private Context context;
    Dialog dialog;

    public Tools(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public static void displayImageBG(Context ctx, ImageView img, String drawable) {
        try {
            Glide.with(ctx).load(drawable).into(img);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimary));
        }
    }


    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }


    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static int dpToPx(Context c, int dp) {
        Resources r = c.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public static void displayImageRound(final Context ctx, final ImageView img, @DrawableRes int drawable) {
        try {
            Glide.with(ctx).asBitmap().load(drawable).into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayImageRoundUrl(final Context ctx, final ImageView img, String urlimg) {
        try {
            Glide.with(ctx).asBitmap().load(urlimg).into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(ctx.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void hideSoftKeyboard(@NonNull Activity activity) {
        @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        if (activity.getCurrentFocus() != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean vpn() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if (iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    public static void toast(Context ctx,String msg,int type) {

        //type 0 info, 1 for Error ,2 for Sucess,3 for warning

        if (type == 0) {
            Toasty.info(ctx, msg, Toast.LENGTH_SHORT, true).show();
        } else if (type == 1) {
            Toasty.error(ctx, msg, Toast.LENGTH_SHORT, true).show();
        } else if (type == 2) {
            Toasty.success(ctx, msg, Toast.LENGTH_SHORT, true).show();
        } else if (type == 3) {
            Toasty.warning(ctx, msg, Toast.LENGTH_SHORT, true).show();
        } else {
            Toast.makeText(ctx, "" + msg, Toast.LENGTH_SHORT).show();
        }

    }

    public static void displayImageOriginal(Context ctx, ImageView img, String drawable) {
        try {
            Glide.with(ctx).load(drawable)
                    .into(img);
        } catch (Exception e) {
        }
    }

        public static void displayImageOriginal(Context ctx, ImageView img, @DrawableRes int drawable) {
        try {
            Glide.with(ctx).load(drawable)
                    .into(img);
        } catch (Exception e) {
        }
    }
    public static void displayImage(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round))
                    .into(img);
        } catch (Exception e) {
        }
    }
    public static void displayImageEvent(Context ctx, ImageView img, String url) {
        try {
            Glide.with(ctx).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                    .into(img);
        } catch (Exception e) {
        }
    }


    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }




    public static void displayImageViewURL(Context ctx,ImageView img, String url) {
        try {
            Glide.with(ctx).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round))
                    .into(img);
        } catch (Exception e) {
        }
    }


    public static void displayImageViewClearcatchURL(Context ctx,ImageView img, String url) {
        try {

            Glide
                    .with(ctx).load(url)
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                    .into(img);
        } catch (Exception e) {
        }
    }



    public static void changeMenuIconColor(Menu menu, @ColorInt int color) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable == null) continue;
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }


    public static void log(String tag,String msg){
        Log.e(tag,""+msg);
    }


    public void exit_dailog() {
        final AlertDialog.Builder bui = new AlertDialog.Builder(context);
        bui.setTitle("Are you sure to delete?");
        bui.setCancelable(false);
        bui.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        bui.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert11 = bui.create();
        alert11.show();
    }


    public static void displayImageURL(Context ctx, CircularImageView img, String url) {

        try {
            Glide.with(ctx).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round))
                    .into(img);
        } catch (Exception e) {
        }
    }

    public void showLoading() {
        dialog.setContentView(R.layout.loadingdailog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void stopLoading() {
        dialog.dismiss();
    }

    public static String capFirstLetter(String str) {
        String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
        return cap;
    }

    public static String toCamelCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }

}
