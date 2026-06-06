package com.silkfinik.financetracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform