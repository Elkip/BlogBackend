package com.mitchellhenschel.features.blog

import kotlinx.serialization.Serializable

@Serializable
data class BlogList(var entries:List<BlogEntity> = ArrayList(),
                    var total: Int = 0)
@Serializable
data class BlogEntity(var _id:String = "",
                      var _created:Int = 0,
                      var title: String = "",
                      var tags: List<String> = ArrayList(),
                      var imageUrl: String = "",
                      var user: String = "",
                      var content: String = "") {
}

data class Tags(val tags: List<String>)
