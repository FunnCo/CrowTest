package com.funnco.crowtest.common.model.request

data class UserRegisterModel (
    val firstname : String,
    val lastname: String,
    val patronymic: String,
    val email: String,
    val phone: String,
    val password: String,
    val group: String
)