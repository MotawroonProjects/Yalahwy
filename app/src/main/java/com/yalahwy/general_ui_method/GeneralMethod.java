package com.yalahwy.general_ui_method;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yalahwy.R;
import com.yalahwy.tags.Tags;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }


    @BindingAdapter({"image", "type"})
    public static void image(View view, String endPoint, int type) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (endPoint != null) {
                if (type == 1) {
                    endPoint = Tags.IMAGE_Slider_URL + endPoint;
                } else if (type == 2) {
                    endPoint = Tags.IMAGE_Maincategory_URL + endPoint;
                } else if (type == 3) {
                    endPoint = Tags.IMAGE_Product_URL + endPoint;

                } else if (type == 4) {
                    endPoint = Tags.IMAGE_Product_Gallery_URL + endPoint;
                }
                else if (type == 5) {
                    endPoint = Tags.IMAGE_Sliderbottom_URL + endPoint;
                }
                else if (type == 6) {
                    endPoint = Tags.IMAGE_Subcategory_URL + endPoint;
                }
                Picasso.get().load(Uri.parse(endPoint)).into(imageView);
            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (endPoint != null) {
                if (type == 1) {
                    endPoint = Tags.IMAGE_Slider_URL + endPoint;
                } else if (type == 2) {
                    endPoint = Tags.IMAGE_Maincategory_URL + endPoint;
                } else if (type == 3) {
                    endPoint = Tags.IMAGE_Product_URL + endPoint;
                } else if (type == 4) {
                    endPoint = Tags.IMAGE_Product_Gallery_URL + endPoint;
                }
                else if (type == 5) {
                    endPoint = Tags.IMAGE_Sliderbottom_URL + endPoint;
                } else if (type == 6) {
                    endPoint = Tags.IMAGE_Subcategory_URL + endPoint;
                }
                Picasso.get().load(Uri.parse(endPoint)).fit().into(imageView);
            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (endPoint != null) {
                if (type == 1) {
                    endPoint = Tags.IMAGE_Slider_URL + endPoint;
                } else if (type == 2) {
                    endPoint = Tags.IMAGE_Maincategory_URL + endPoint;
                } else if (type == 3) {
                    endPoint = Tags.IMAGE_Product_URL + endPoint;

                } else if (type == 4) {
                    endPoint = Tags.IMAGE_Product_Gallery_URL + endPoint;
                }
                else if (type == 5) {
                    endPoint = Tags.IMAGE_Sliderbottom_URL + endPoint;
                } else if (type == 6) {
                    endPoint = Tags.IMAGE_Subcategory_URL + endPoint;
                }
                /*DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity)  view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                int hight=displayMetrics.heightPixels;
                Log.e("lsllsls",hight+"");*/
                Picasso.get().load(Uri.parse(endPoint)).resize(512,512).centerCrop().into(imageView);
            }
        }

    }

    @BindingAdapter("day")
    public static void day(View view, String date) {
        if (date != null && !date.isEmpty()) {
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            try {
                Date date1 = format1.parse(date);
                Calendar calendar = Calendar.getInstance();
                long diff = 0;
                if (date1.getTime() > calendar.getTime().getTime()) {
                    diff = date1.getTime() - calendar.getTime().getTime();
                } else {
                    diff = calendar.getTime().getTime() - date1.getTime();

                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
                String day = simpleDateFormat.format(new Date(diff));

                TextView textView = (TextView) view;
                textView.setText(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @BindingAdapter("hour")
    public static void hour(View view, String date) {
        if (date != null && !date.isEmpty()) {
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            try {
                Date date1 = format1.parse(date);
                Calendar calendar = Calendar.getInstance();
                long diff = 0;
                if (date1.getTime() > calendar.getTime().getTime()) {
                    diff = date1.getTime() - calendar.getTime().getTime();
                } else {
                    diff = calendar.getTime().getTime() - date1.getTime();

                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh", Locale.ENGLISH);
                String hour = simpleDateFormat.format(new Date(diff));

                TextView textView = (TextView) view;
                textView.setText(hour);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @BindingAdapter("minutes")
    public static void minutes(View view, String date) {
        if (date != null && !date.isEmpty()) {
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            try {
                Date date1 = format1.parse(date);
                Calendar calendar = Calendar.getInstance();
                long diff = 0;
                if (date1.getTime() > calendar.getTime().getTime()) {
                    diff = date1.getTime() - calendar.getTime().getTime();
                } else {
                    diff = calendar.getTime().getTime() - date1.getTime();

                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm", Locale.ENGLISH);
                String minutes = simpleDateFormat.format(new Date(diff));

                TextView textView = (TextView) view;
                textView.setText(minutes);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @BindingAdapter("rate")
    public static void rate(SimpleRatingBar simpleRatingBar, double rate) {
        simpleRatingBar.setRating((float) rate);
    }


    @BindingAdapter({"amount", "price"})
    public static void calculateCartItemCost(TextView textView, int amount, double price) {
        double cost = amount * price;
        textView.setText(String.format(Locale.ENGLISH, "%.2f %s", cost, textView.getContext().getString(R.string.sar)));
    }


}










