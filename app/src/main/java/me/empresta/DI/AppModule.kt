package me.empresta.DI

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
import me.empresta.DAO.ItemAnnouncementDAO
import me.empresta.DAO.ItemRequestDAO
import me.empresta.DAO.VouchDAO
import me.empresta.PubSub.Message_Handler
import me.empresta.PubSub.PubSub
import me.empresta.RemoteAPI.CommunityAPI
import me.empresta.feature_QRCode_Connection.use_case.ConnectToCommunity
import me.empresta.feature_QRCode_Connection.use_case.VouchUseCase
import me.empresta.feature_View_Profile.use_case.ProfileUseCase
import me.empresta.feature_register.use_case.RegisterUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    @Singleton
    @Provides
    fun provideItemRequestDAO(db: Database): ItemRequestDAO { return db.ItemRequestDAO }
    @Singleton
    @Provides
    fun provideItemAnnouncementDAO(db: Database): ItemAnnouncementDAO { return db.ItemAnnouncementDAO }


    @Provides
    @Singleton
    fun provideRegisterUseCases(repository: Repository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    fun provideBuilder(): CommunityAPI {
        return  Retrofit.Builder().baseUrl("http://empresta.me").addConverterFactory(GsonConverterFactory.create()).build().create(CommunityAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectToCommunityUseCase(repository: Repository,communityAPI:CommunityAPI,communityDao: CommunityDao): ConnectToCommunity {
        return ConnectToCommunity(repository,communityDao)
    }

    @Provides
    @Singleton
    fun provideProfileUseCase(repository: Repository): ProfileUseCase {
        return ProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVouchUseCase(repository: Repository,pubSub: PubSub): VouchUseCase {
        return VouchUseCase(repository, pubSub )
    }
    @Singleton
    @Provides
    fun provideVouchDao(db: Database): VouchDAO { return db.VouchDAO }

    @Provides
    @Singleton
    fun provideMessageHandler(repository: Repository): Message_Handler {
        return Message_Handler(repository)
    }

    @Provides
    @Singleton
    fun providePubSub(messageHandler: Message_Handler): PubSub {
        return PubSub(messageHandler)
    }
}