package com.theone.domain.remote

import android.content.Context
import com.theone.domain.utils.Data
import com.theone.domain.utils.FileData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object FileDataModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserData

    @UserData
    @Provides
    fun providesUserData(@ApplicationContext context: Context): FileData {
        return FileData(context, Data.USER_DATA)
    }
}