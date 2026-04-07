package com.kolo.prayer.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class BookRepository_Factory implements Factory<BookRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public BookRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public BookRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static BookRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new BookRepository_Factory(firestoreProvider);
  }

  public static BookRepository newInstance(FirebaseFirestore firestore) {
    return new BookRepository(firestore);
  }
}
