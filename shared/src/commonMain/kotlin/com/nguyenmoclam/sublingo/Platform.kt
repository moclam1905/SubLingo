package com.nguyenmoclam.sublingo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform