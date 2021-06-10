package com.arjteam.music.playmusicpro.glide;

import static com.arjteam.music.playmusicpro.Constants.USER_PROFILE;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.arjteam.music.appthemehelper.ThemeStore;
import com.arjteam.music.appthemehelper.util.TintHelper;
import com.arjteam.music.playmusicpro.App;
import com.arjteam.music.playmusicpro.R;
import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.MediaStoreSignature;
import java.io.File;

public class UserProfileGlideRequest {
  private static final DiskCacheStrategy DEFAULT_DISK_CACHE_STRATEGY = DiskCacheStrategy.NONE;
  private static final int DEFAULT_ERROR_IMAGE = R.drawable.ic_account;
  private static final int DEFAULT_ANIMATION = android.R.anim.fade_in;

  public static File getUserModel() {
    File dir = App.Companion.getContext().getFilesDir();
    return new File(dir, USER_PROFILE);
  }

  private static BitmapTypeRequest<File> createBaseRequest(
      RequestManager requestManager, File profile) {
    return requestManager.load(profile).asBitmap();
  }

  private static Key createSignature(File file) {
    return new MediaStoreSignature("", file.lastModified(), 0);
  }

  public static class Builder {
    private RequestManager requestManager;
    private File profile;
    private Drawable error;

    private Builder(RequestManager requestManager, File profile) {
      this.requestManager = requestManager;
      this.profile = profile;
      error =
          TintHelper.createTintedDrawable(
              App.Companion.getContext(),
              R.drawable.ic_account,
              ThemeStore.Companion.accentColor(App.Companion.getContext()));
    }

    public static Builder from(@NonNull RequestManager requestManager, File profile) {
      return new Builder(requestManager, profile);
    }

    @NonNull
    public BitmapRequestBuilder<File, Bitmap> build() {
      return createBaseRequest(requestManager, profile)
          .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
          .error(error)
          .animate(DEFAULT_ANIMATION)
          .signature(createSignature(profile));
    }
  }

  public static class BitmapBuilder {
    private final Builder builder;

    BitmapBuilder(Builder builder) {
      this.builder = builder;
    }

    public BitmapRequestBuilder<?, Bitmap> build() {
      return createBaseRequest(builder.requestManager, builder.profile)
          .diskCacheStrategy(DEFAULT_DISK_CACHE_STRATEGY)
          .error(builder.error)
          .animate(DEFAULT_ANIMATION)
          .signature(createSignature(builder.profile));
    }
  }
}
