package me.empresta.feature_View_Network.use_case

import me.empresta.DAO.Account
import me.empresta.DAO.Community
import me.empresta.DI.Repository
import me.empresta.PubSub.PubSub
import javax.inject.Inject

class NetworkUseCase @Inject constructor(private val repository: Repository, private val pubSub: PubSub) {


    suspend fun getFirstCommunity(): Community {

        val communities =   repository.getCommunities()


        return communities[0]
    }

    suspend fun getMyInfo(): Account {

        return repository.getAccount();

    }



}