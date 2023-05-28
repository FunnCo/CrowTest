package com.funnco.crowtest.common.model.response

data class UserInfoModel (
    val firstname : String,
    val lastname: String,
    val patronymic: String,
    val email: String,
    val phone: String,
    val userId: String,
    val groupName: String
)