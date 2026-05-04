package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class SupabaseCategoryRepositoryImpl @Inject constructor(
    private val client: SupabaseClient
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = flow {
        val categories = client.from("categories")
            .select()
            .decodeAs<List<Category>>()

        emit(categories)
    }

    override fun getCategoryById(id: String): Category? = runBlocking {
        client.from("categories")
            .select {
                filter {
                    eq("id", id)
                }
            }
            .decodeAs<List<Category>>()
            .firstOrNull()
    }
}