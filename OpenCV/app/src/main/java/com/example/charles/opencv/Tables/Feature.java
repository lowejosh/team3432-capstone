package com.example.charles.opencv.Tables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseArray;

import java.io.IOException;

/**
 * This class allows the conversion from database ENUM values to their meaning. To use this class, run loadFeatures() when the database is loaded.
 */
public final class Feature {
    public static Integer UNKNOWN;
    public static Integer OTHER;

    private static SparseArray<String> featureName;
    private static SparseArray<String> featureImage;
    private static boolean loaded = false;
    private static Bitmap noImage;

    public static void loadFeatures(SparseArray<String> features, SparseArray<String> images) {
        //Check if options array has been already loaded
        if (loaded)
            throw new RuntimeException("Feature [Feature(SparseArray<String> features)]: Do not load feature array multiple times.");

        //Check if there is at minimum an unknown, other, and another feature
        if (features.size() < 3)
            throw new RuntimeException("Feature [Feature(SparseArray<String> features)]: Feature array does not contain any features.");

        //Check both arrays are the same size
        if (features.size() != images.size())
            throw new RuntimeException("Feature [Feature(SparseArray<String> features)]: Arrays are not the same size.");

        for (int i = 0; i < features.size(); i++) {
            if (features.valueAt(i).equals("Unknown"))
                UNKNOWN = features.keyAt(i);
            if (features.valueAt(i).equals("Other"))
                OTHER = features.keyAt(i);
        }

        if (UNKNOWN == null || OTHER == null)
            throw new RuntimeException("Feature [Feature(SparseArray<String> features)]: Feature array does not contain an Unknown or Other option.");

        Log.i("Unknown", String.valueOf(UNKNOWN));
        Log.i("Other", String.valueOf(OTHER));

        featureName  = features;
        featureImage = images;
        loaded       = true;

        Log.i("Features", featureName.toString());
    }

    /**
     * Returns true if the feature list has been loaded.
     * @return True if the feature list has been loaded
     */
    public static boolean isLoaded() {
        return loaded;
    }

    /**
     * Get Value of Option, ie 0->"Other"
     *
     * @param option Feature Option
     * @return Value of the Feature
     */
    public static String valueOf(Integer option) {
        if (!loaded)
            throw new RuntimeException("Feature [valueOf(Integer option)]: Feature set not loaded yet.");

        //Return Value of the Feature, if None Exists Return UNKNOWN
        return featureName.get(option, "Unknown");
    }

    /**
     * Get an image of the feature.
     *
     * @param context Program context
     * @param option Feature Option
     * @return Image of the Feature
     */
    public static Bitmap getImage(Context context, Integer option) {
        if (!loaded)
            throw new RuntimeException("Feature [valueOf(Integer option)]: Feature set not loaded yet.");

        //Update image for ImageView
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(featureImage.get(option)));
        } catch (IOException | IllegalArgumentException unused) {
            //If bird image does not exist, display noImage file
            Log.e("MainActivity", "Failed to load image: " + featureName.get(option));

            try {
                Bitmap temp = BitmapFactory.decodeStream(context.getAssets().open("features/noImage.png"));
                return temp;
            } catch (IOException ex) {
                Log.e("MainActivity", "noImage Failed to Load");
                Log.e("MainActivity", ex.getMessage());
            }
        }

        return null;
    }

    /**
     * Returns true if the feature is an "unknown" feature
     *
     * @param feature Index of feature in FeatureOption
     * @return True if the feature is an "unknown" feature
     */
    public static boolean isUnknown(Integer feature) {
        if (!loaded)
            throw new RuntimeException("Feature [isUnknown(Integer feature)]: Feature set not loaded yet.");

        return feature.equals(UNKNOWN);
    }

    /**
     * Returns true if the feature is an "other" feature
     *
     * @param feature Index of feature in FeatureOption
     * @return True if the feature is an "other" feature
     */
    public static boolean isOther(Integer feature) {
        if (!loaded)
            throw new RuntimeException("Feature [isOther(Integer feature)]: Feature set not loaded yet.");

        return feature.equals(OTHER);
    }

    /**
     * DO NOT USE THIS FUNCTION. DESIGNED FOR JUNIT TESTING ONLY.
     * Resets class to unset state so it can be loaded again.
     */
    public static void reset() {
        if (loaded) {
            featureName.clear();
            UNKNOWN = null;
            OTHER   = null;
            loaded  = false;
        }
    }
}
