package com.brandonwong.langassignment.data.provider

import com.brandonwong.langassignment.data.moshi.DefaultIfNullFactory
import com.squareup.moshi.Moshi

object MoshiProvider {
    val moshiDefaultIfNull: Moshi = Moshi.Builder()
        .add(DefaultIfNullFactory())
        .build()
}