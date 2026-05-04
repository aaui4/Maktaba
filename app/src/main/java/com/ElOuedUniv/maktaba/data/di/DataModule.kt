package com.ElOuedUniv.maktaba.data.di

import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository
import com.ElOuedUniv.maktaba.data.repository.SupabaseBookRepositoryImpl
import com.ElOuedUniv.maktaba.data.repository.SupabaseCategoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        impl: SupabaseCategoryRepositoryImpl
    ): CategoryRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        impl: SupabaseBookRepositoryImpl
    ): BookRepository {
        return impl
    }
}