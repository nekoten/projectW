package com.example.warehouseapplication.Tool;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;


/**
 * Created by ptwitchapon on 11/4/2561.
 */

public class Gallery {
    public static final String IMAGE_JPG = "jpg";
    public static final String IMAGE_JPEG = "jpeg";
    public static final String IMAGE_PNG = "png";
    private static final String TAG = "Camera";
    /**
     * default values used by camera
     */
    private static final String IMAGE_FORMAT_JPG = ".jpg";
    private static final String IMAGE_FORMAT_JPEG = ".jpeg";
    private static final String IMAGE_FORMAT_PNG = ".png";
    private static final int IMAGE_HEIGHT = 1000;
    private static final int IMAGE_COMPRESSION = 75;
    private static final String IMAGE_DEFAULT_DIR = "capture";
    private static final String IMAGE_DEFAULT_NAME = "img_";
    /**
     * public variables to be used in the builder
     */
    public static int REQUEST_PICK_PHOTO = 123;
    /**
     * Private variables
     */
    private Context context;
    private Activity activity;
    private Fragment fragment;
    private androidx.core.content.FileProvider compatFragment;
    private String cameraBitmapPath = null;
    private Bitmap cameraBitmap = null;
    private String dirName;
    private String imageName;
    private String imageType;
    private int imageHeight;
    private int compression;
    private boolean isCorrectOrientationRequired;
    private MODE mode;

    private String authority;

    /**
     * @param builder to copy all the values from.
     */
    private Gallery(Builder builder) {
        activity = builder.activity;
        context = builder.context;
        mode = builder.mode;
        fragment = builder.fragment;
        compatFragment = builder.compatFragment;
        dirName = builder.dirName;
        REQUEST_PICK_PHOTO = builder.REQUEST_PICK_PHOTO;
        imageName = builder.imageName;
        imageType = builder.imageType;
        isCorrectOrientationRequired = builder.isCorrectOrientationRequired;
        compression = builder.compression;
        imageHeight = builder.imageHeight;
        authority = context.getApplicationContext().getPackageName() + ".imageprovider";
    }
    public void setPath(String cameraBitmapPath){
        this.cameraBitmapPath = cameraBitmapPath;
    }


    private void setUpIntent(Intent PICKPictureIntent) throws NullPointerException {
        File photoFile = Utils.createImageFile(context, dirName, imageName, imageType);
        if (photoFile != null) {

            cameraBitmapPath = photoFile.getAbsolutePath();

            Uri uri= FileProvider.getUriForFile(context, authority, photoFile);

            PICKPictureIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    uri);
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(PICKPictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        else {
            throw new NullPointerException("Image file could not be created");
        }
    }
    /**
     * Initiate the existing camera apps
     *
     * @throws //NullPointerException
     */
    public void pickPicture() throws NullPointerException, IllegalAccessException {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//        activity.startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);

        switch (mode) {
            case ACTIVITY:
                if (galleryIntent.resolveActivity(activity.getPackageManager()) != null) {
                    setUpIntent(galleryIntent);
                    activity.startActivityForResult(galleryIntent,REQUEST_PICK_PHOTO);
                } else {
                    throw new IllegalAccessException("Unable to open camera");
                }
                break;

            case FRAGMENT:
                if (galleryIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
                    setUpIntent(galleryIntent);
                    fragment.startActivityForResult(galleryIntent,REQUEST_PICK_PHOTO);
                } else {
                    throw new IllegalAccessException("Unable to open camera");
                }
                break;

//            case COMPAT_FRAGMENT:
//                if (galleryIntent.resolveActivity(compatFragment.getActivity().getPackageManager()) != null) {
//                    setUpIntent(galleryIntent);
//                    compatFragment.startActivityForResult(galleryIntent,REQUEST_PICK_PHOTO);
//                } else {
//                    throw new IllegalAccessException("Unable to open camera");
//                }
//                break;
        }

    }

    /**
     * @return the saved bitmap path but scaling bitmap as per builder
     */
    public String getCameraBitmapPath() {
//        Bitmap bitmap = getCameraBitmap();
//        bitmap.recycle();
        return cameraBitmapPath;
    }

    /**
     * @return The scaled bitmap as per builder
     */
    public Bitmap getCameraBitmap() {
        return resizeAndGetCameraBitmap(imageHeight);
    }

    /**
     * @param imageHeight
     * @return Bitmap path with approx desired height
     */
    public String resizeAndGetCameraBitmapPath(int imageHeight) {
        Bitmap bitmap = resizeAndGetCameraBitmap(imageHeight);
        bitmap.recycle();
        return cameraBitmapPath;
    }

    /**
     * @param imageHeight
     * @return Bitmap with approx desired height
     */
    public Bitmap resizeAndGetCameraBitmap(int imageHeight) {
        try {
            if (cameraBitmap != null) {
                cameraBitmap.recycle();
            }
            cameraBitmap = Utils.decodeFile(new File(cameraBitmapPath), imageHeight);
            if (cameraBitmap != null) {
                if (isCorrectOrientationRequired) {
                    cameraBitmap = Utils.rotateBitmap(cameraBitmap, Utils.getImageRotation(cameraBitmapPath));
                }
                Utils.saveBitmap(cameraBitmap, cameraBitmapPath, imageType, compression);
            }
            return cameraBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Deletes the saved camera image
     */
    public void deleteImage() {
        if (cameraBitmapPath != null) {
            File image = new File(cameraBitmapPath);
            if (image.exists()) {
                image.delete();
            }
        }
    }

    private enum MODE {ACTIVITY, FRAGMENT, COMPAT_FRAGMENT}

    /**
     * Camera builder declaration
     */
    public static class Builder {
        private Context context;
        private Activity activity;
        private Fragment fragment;
        private FileProvider compatFragment;
        private String dirName;
        private String cameraBitmapPath;
        private String imageName;
        private String imageType;
        private int imageHeight;
        private int compression;
        private boolean isCorrectOrientationRequired;
        private MODE mode;
        private int REQUEST_PICK_PHOTO;

        public Builder() {
            dirName = Gallery.IMAGE_DEFAULT_DIR;
            imageName = Gallery.IMAGE_DEFAULT_NAME + System.currentTimeMillis();
            imageHeight = Gallery.IMAGE_HEIGHT;
            compression = Gallery.IMAGE_COMPRESSION;
            imageType = Gallery.IMAGE_FORMAT_JPG;
            REQUEST_PICK_PHOTO = Gallery.REQUEST_PICK_PHOTO;
        }

        public Builder setDirectory(String dirName) {
            if (dirName != null)
                this.dirName = dirName;
            return this;
        }

        public Builder setpickPhotoRequestCode(int requestCode) {
            this.REQUEST_PICK_PHOTO = requestCode;
            return this;
        }

        public Builder setName(String imageName) {
            if (imageName != null)
                this.imageName = imageName;
            return this;
        }

        public Builder resetToCorrectOrientation(boolean reset) {
            this.isCorrectOrientationRequired = reset;
            return this;
        }

        public Builder setImageFormat(String imageFormat) {

            if (TextUtils.isEmpty(imageFormat)) {
                return this;
            }

            switch (imageFormat) {
                case "png":
                case "PNG":
                case ".png":
                    this.imageType = IMAGE_FORMAT_PNG;
                    break;
                case "jpg":
                case "JPG":
                case ".jpg":
                    this.imageType = IMAGE_FORMAT_JPG;
                    break;
                case "jpeg":
                case "JPEG":
                case ".jpeg":
                    this.imageType = IMAGE_FORMAT_JPEG;
                    break;
                default:
                    this.imageType = IMAGE_FORMAT_JPG;
            }
            return this;
        }

        public Builder setImagepath(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }
        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public Builder setCompression(int compression) {
            if (compression > 100) {
                compression = 100;
            } else if (compression < 0) {
                compression = 0;
            }
            this.compression = compression;
            return this;
        }

        public Gallery build(Activity activity) {
            this.activity = activity;
            context = activity.getApplicationContext();
            mode = MODE.ACTIVITY;
            return new Gallery(this);
        }

        public Gallery build(Fragment fragment) {
            this.fragment = fragment;
            context = fragment.getActivity().getApplicationContext();
            mode = MODE.FRAGMENT;
            return new Gallery(this);
        }

        public Gallery build(FileProvider fragment) {
            compatFragment = fragment;
            //context = fragment.getActivity().getApplicationContext();
            mode = MODE.COMPAT_FRAGMENT;
            return new Gallery(this);
        }
    }
}
