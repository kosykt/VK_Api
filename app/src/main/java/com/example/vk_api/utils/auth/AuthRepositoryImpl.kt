package com.example.vk_api.utils.auth

import android.content.SharedPreferences

/**
 * Представляет сервис сохранения пользовательских настроек.
 * @param sharedPreference Класс записи пользовательских настроек.
 */
internal class AuthRepositoryImpl(
    private val sharedPreference: SharedPreferences
) : AuthRepository {
    private val TOKEN = "token"
    private val USER_ID = "userId"

    override var token: String?
        get() {
            return sharedPreference.getString(TOKEN, null)
        }
        set(value) {
            with(sharedPreference.edit()) {
                if (value == null) {
                    remove(TOKEN)
                }
                else {
                    putString(TOKEN, value)
                }
                apply()
            }
        }

    override var userId: String?
        get() {
            return sharedPreference.getString(USER_ID, null)
        }
        set(value) {
            with(sharedPreference.edit()) {
                if (value == null) {
                    remove(USER_ID)
                }
                else {
                    putString(USER_ID, value)
                }
                apply()
            }
        }
}
