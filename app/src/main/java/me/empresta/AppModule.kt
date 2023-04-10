package me.empresta

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.empresta.DAO.AccountDao
import me.empresta.DAO.CommunityDao
import me.empresta.DAO.Database
import me.empresta.feature_register.use_case.RegisterUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "EMPRESTAMEBB"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideCommunityDao(db: Database): CommunityDao { return db.CommunityDao } // The reason we can implement a Dao for the database

    @Singleton
    @Provides
    fun provideAccountDao(db: Database): AccountDao{ return db.AccountDao }

    @Provides
    @Singleton
    fun provideRegisterUseCases(dao: AccountDao): RegisterUseCase {
        return RegisterUseCase(dao)
    }

}