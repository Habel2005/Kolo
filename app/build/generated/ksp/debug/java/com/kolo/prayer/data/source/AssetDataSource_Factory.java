package com.kolo.prayer.data.source;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AssetDataSource_Factory implements Factory<AssetDataSource> {
  private final Provider<Context> contextProvider;

  public AssetDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AssetDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static AssetDataSource_Factory create(Provider<Context> contextProvider) {
    return new AssetDataSource_Factory(contextProvider);
  }

  public static AssetDataSource newInstance(Context context) {
    return new AssetDataSource(context);
  }
}
