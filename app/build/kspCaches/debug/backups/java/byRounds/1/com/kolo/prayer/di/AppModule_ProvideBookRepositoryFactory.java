package com.kolo.prayer.di;

import com.kolo.prayer.data.repository.BookRepository;
import com.kolo.prayer.data.source.AssetDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AppModule_ProvideBookRepositoryFactory implements Factory<BookRepository> {
  private final Provider<AssetDataSource> assetDataSourceProvider;

  public AppModule_ProvideBookRepositoryFactory(Provider<AssetDataSource> assetDataSourceProvider) {
    this.assetDataSourceProvider = assetDataSourceProvider;
  }

  @Override
  public BookRepository get() {
    return provideBookRepository(assetDataSourceProvider.get());
  }

  public static AppModule_ProvideBookRepositoryFactory create(
      Provider<AssetDataSource> assetDataSourceProvider) {
    return new AppModule_ProvideBookRepositoryFactory(assetDataSourceProvider);
  }

  public static BookRepository provideBookRepository(AssetDataSource assetDataSource) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBookRepository(assetDataSource));
  }
}
