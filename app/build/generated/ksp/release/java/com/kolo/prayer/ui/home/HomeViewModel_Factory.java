package com.kolo.prayer.ui.home;

import com.kolo.prayer.data.repository.BookRepository;
import com.kolo.prayer.data.repository.PreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<BookRepository> bookRepositoryProvider;

  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public HomeViewModel_Factory(Provider<BookRepository> bookRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(bookRepositoryProvider.get(), preferencesRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<BookRepository> bookRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new HomeViewModel_Factory(bookRepositoryProvider, preferencesRepositoryProvider);
  }

  public static HomeViewModel newInstance(BookRepository bookRepository,
      PreferencesRepository preferencesRepository) {
    return new HomeViewModel(bookRepository, preferencesRepository);
  }
}
