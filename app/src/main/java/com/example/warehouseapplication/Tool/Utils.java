package com.example.warehouseapplication.Tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.widget.Toast;

import com.example.warehouseapplication.Model.DeliveryOrderModel;
import com.example.warehouseapplication.Model.DriverModel;
import com.example.warehouseapplication.Model.MyDeliverDriver;
import com.example.warehouseapplication.Model.Order;
import com.example.warehouseapplication.Model.Orderlist;
import com.example.warehouseapplication.Model.Product;
import com.example.warehouseapplication.Model.QrScan;
import com.example.warehouseapplication.Model.StockModel;
import com.example.warehouseapplication.Model.StoreModel;
import com.example.warehouseapplication.Model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptwitchapon on 9/2/2561.
 */

public class Utils {
    public static JSONObject object = new JSONObject();
    public static JSONObject object2 = new JSONObject();
    public static JSONArray array=new JSONArray();

    public static JSONArray arrayVeg  = new JSONArray();
    public static JSONArray arrayEx = new JSONArray();


    public static String username;
    public static String password;

    public static double lat1 = 13.761519;
    public static double lng1 = 100.548816;

    public static double latview ;
    public static double lngview ;


    public static Order order = new Order();
    public static List<Order.OrderBean> orderbanlist = new ArrayList<>();
    public static Order.OrderBean orderbean = new Order.OrderBean();

    public static int arrayindex =0;
    public static boolean addup = false;

    public static QrScan itemScan;

    public static User user;
    public static String order_id;

    public static String mylattitude,mylongitude;

    public static String ipHost ="http://burgest.apidech.com/inc/";
    public static String ipTopupPic = "http://burgest.apidech.com/paymentimg/";
    public static String ipPic = "http://burgest.apidech.com/img/";



    public static StoreModel storeModel ;
    public static Product product;
    public static DriverModel driver;
    public static DeliveryOrderModel driver_allOrder;
    public static MyDeliverDriver driver_myOrder;
    public static StockModel stock;

    public static StockModel stock_edit;
    public static Orderlist orderlist;

    public static boolean isLogin = false;

    public static boolean Checklocation(double lat,double lng){
        Location Burgest = new Location("Burgest");
        Burgest.setLatitude(Double.valueOf(Utils.storeModel.getStore().get(0).getLatitude()));
        Burgest.setLongitude(Double.valueOf(Utils.storeModel.getStore().get(0).getLongitude()));
        Burgest.setAltitude(0);

        Location chk = new Location("chk");
        chk.setLatitude(lat);
        chk.setLongitude(lng);
        chk.setAltitude(0);

        if (Burgest.distanceTo(chk)< Double.valueOf(Utils.storeModel.getStore().get(0).getDistance())) {
            //in
            return true;
        }else {
            //out
            return false;
        }
    }

    public static void toast(Context context, String string) {
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);

        toast.show();

    }

    private static final String TAG = "Utils";

    /**
     * @param context
     * @param dirName
     * @param fileName
     * @param fileType
     * @return
     */
    public static File createImageFile(
            Context context,
            String dirName,
            String fileName,
            String fileType) {
        try {
            File file = createDir(context, dirName);
            File image = new File(file.getAbsoluteFile() + File.separator + fileName + fileType);
            if (!image.getParentFile().exists()) {
                image.getParentFile().mkdirs();
            }
            image.createNewFile();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param context
     * @param dirName
     * @return
     */
    public static File createDir(Context context, String dirName) {
        File file = new File(context.getFilesDir() + File.separator + dirName);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /**
     * @param file
     * @param requiredHeight
     * @return
     */
    public static Bitmap decodeFile(File file, int requiredHeight) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= requiredHeight &&
                    o.outHeight / scale / 2 >= requiredHeight) {
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param bitmap
     * @param filePath
     * @param imageType
     * @param compression
     */
    public static void saveBitmap(Bitmap bitmap, String filePath, String imageType, int compression) {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            // PNG is a loss less format, the compression factor (100) is ignored
            switch (imageType) {
                case "png":
                case "PNG":
                case ".png":
                    bitmap.compress(Bitmap.CompressFormat.PNG, compression, out);
                    break;
                case "jpg":
                case "JPG":
                case ".jpg":
                case "jpeg":
                case "JPEG":
                case ".jpeg":
                    bitmap.compress(Bitmap.CompressFormat.JPEG, compression, out);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param imagePath
     * @return
     */
    public static int getImageRotation(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ExifInterface exif = new ExifInterface(imageFile.getPath());
                int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                return exifToDegrees(rotation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param src
     * @param rotation
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap src, int rotation) {
        Matrix matrix = new Matrix();
        if (rotation != 0) {
            matrix.preRotate(rotation);
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }
        return src;
    }

    /**
     * @param exifOrientation
     * @return
     */
    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }

        return 0;
    }


}
