package com.shegor.gitrepos.utils

object UrlParseUtils {

    fun parseLinkHeader(linkHeader: String?): Map<String, String> {
        val header = linkHeader ?: return mapOf()
        val result = mutableMapOf<String, String>()

        val linksWithRel = header.split(",")

        linksWithRel.forEach {
            val link = Regex("<.*>").find(it)?.value?.removeSurrounding("<", ">")
            val rel = Regex("\".*\"").find(it)?.value?.removeSurrounding("\"")

            if (link !== null && rel != null) result[rel] = link
        }
        return result
    }

    fun cutUrl(url: String?): String {
        url?.let {
            return Regex("\\{.*\\}").replace(it, "")
        }
        return ""
    }
}