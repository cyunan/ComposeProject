package com.cyn.composeproject

data class BannerData(
    var banners: List<Banner>? = null,
    var code: Int = 0
){
    companion object {
        const val TYPE_PC = 0
        const val TYPE_ANDROID = 1
        const val TYPE_IPHONE = 2
        const val TYPE_IPAD = 3
    }
}

data class Banner(
    var imageUrl: String? = null,
    var typeTitle: String? = null,
    var target: Long = 0
)
