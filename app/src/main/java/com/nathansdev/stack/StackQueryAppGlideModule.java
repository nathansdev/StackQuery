package com.nathansdev.stack;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Glide module for StackQuery app.
 */
@GlideModule
public class StackQueryAppGlideModule extends AppGlideModule {

    /**
     * Disabling manifest parsing to avoid adding same module twice.
     *
     * @return true or false.
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
