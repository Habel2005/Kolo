package com.kolo.prayer.ui.bookdetail;

import androidx.lifecycle.SavedStateHandle;
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
public final class BookDetailViewModel_Factory implements Factory<BookDetailViewModel> {
  private final Provider<BookRepository> bookRepositoryProvider;

  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public BookDetailViewModel_Factory(Provider<BookRepository> bookRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public BookDetailViewModel get() {
    return newInstance(bookRepositoryProvider.get(), preferencesRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static BookDetailViewModel_Factory create(Provider<BookRepository> bookRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new BookDetailViewModel_Factory(bookRepositoryProvider, preferencesRepositoryProvider, savedStateHandleProvider);
  }

  public static BookDetailViewModel newInstance(BookRepository bookRepository,
      PreferencesRepository preferencesRepository, SavedStateHandle savedStateHandle) {
    return new BookDetailViewModel(bookRepository, preferencesRepository, savedStateHandle);
  }
}
