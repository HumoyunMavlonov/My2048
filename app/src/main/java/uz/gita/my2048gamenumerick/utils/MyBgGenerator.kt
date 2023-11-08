package uz.gita.my2048gamenumerick.utils

import uz.gita.my2048gamenumerick.R

class MyBgGenerator {
    private val hashMap = hashMapOf(
        0 to R.drawable.bg_item_0,
        2 to R.drawable.bg_item_2,
        4 to R.drawable.bg_item_4,
        8 to R.drawable.bg_item_8,
        16 to R.drawable.bg_item_16,
        32 to R.drawable.bg_item_32,
        64 to R.drawable.bg_item_64,
        128 to R.drawable.bg_item_128,
        256 to R.drawable.bg_item_256,
        512 to R.drawable.bg_item_512,
        1024 to R.drawable.bg_item_1024,
        2048 to R.drawable.bg_item_2048,
        4096 to R.drawable.bg_item_4096,
        8192 to R.drawable.bg_item_8192,
        16384 to R.drawable.bg_item_2
    )

    fun getBgByAmount(amount: Int): Int = hashMap.getOrDefault(amount, R.drawable.bg_egg)
}