package com.ElOuedUniv.maktaba.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://epatonqroshbvmysojnr.supabase.co",
            supabaseKey = "sb_publishable_PsJcmIXctk8c3i-Ouq_98w_o7xxWSfX"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}
