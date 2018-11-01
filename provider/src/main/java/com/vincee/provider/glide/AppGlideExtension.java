package com.vincee.provider.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class AppGlideExtension {


    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;

    private AppGlideExtension() {
    }


    @GlideOption
    public static RequestOptions miniThumb(RequestOptions options) {
       return options.fitCenter().override(MINI_THUMB_SIZE);
    }

}
