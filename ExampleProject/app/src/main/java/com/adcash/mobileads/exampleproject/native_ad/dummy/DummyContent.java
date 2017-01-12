package com.adcash.mobileads.exampleproject.native_ad.dummy;

import com.adcash.mobileads.exampleproject.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Helper class for providing sample content for user interfaces
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    private static final int COUNT = 19;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position), R.drawable.ic_image_placeholder);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        builder.append("\nMore details information here.");
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String title;
        public final String description;
        public final int imageId;

        public DummyItem(String id, String title, String description, int imageId) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.imageId = imageId;
        }
    }
}
