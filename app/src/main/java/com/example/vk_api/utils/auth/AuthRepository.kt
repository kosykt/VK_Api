package com.example.vk_api.utils.auth

interface AuthRepository {
    /**
     * Возвращает или устанавливает токен.
     */
    var token: String?
    /**
     * Возвращает или устанавливает идентификатор пользователя.
     */
    var userId: String?
}
