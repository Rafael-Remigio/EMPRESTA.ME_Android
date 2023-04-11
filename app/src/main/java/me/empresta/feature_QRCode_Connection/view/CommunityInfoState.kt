package me.empresta.feature_QRCode_Connection.view

import me.empresta.RemoteAPI.DTO.CommunityInfo

data class CommunityInfoState(
    val isLoading: Boolean = false,
    val info: CommunityInfo? = null,
    val error: String = ""
)
