package me.empresta.feature_View_Profile.use_case

import me.empresta.DAO.Account
import me.empresta.DI.Repository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getProfileInfo() : Account{

        return repository.getAccount()
    }

}