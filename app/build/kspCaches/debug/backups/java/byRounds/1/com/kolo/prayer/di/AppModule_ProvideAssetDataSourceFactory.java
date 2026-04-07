package com.kolo.prayer.di;

import android.content.Context;
import com.kolo.prayer.data.source.AssetDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideAssetDataSourceFactory implements Factory<AssetDataSource> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideAssetDataSourceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AssetDataSource get() {
    return provideAssetDataSource(contextProvider.get());
  }

  public static AppModule_ProvideAssetDataSourceFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideAssetDataSourceFactory(contextProvider);
  }

  public static AssetDataSource provideAssetDataSource(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAssetDataSource(context));
  }
}
