package com.vincee.provider.glide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class MGlideModule extends AppGlideModule {


    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        RequestOptions options =
                new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).placeholder(new ColorDrawable(Color.GRAY));
        builder.setDefaultRequestOptions(options);
    }

}
