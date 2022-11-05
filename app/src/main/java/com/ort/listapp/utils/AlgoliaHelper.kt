package com.ort.listapp.utils

import com.algolia.search.client.ClientSearch
import com.algolia.search.client.Index
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName

object AlgoliaHelper {

    private val client = ClientSearch(
        applicationID = ApplicationID("ENBZ8I8018"),
        apiKey = APIKey("404abf3f51b527e0cc1f492c64d0b849"),
    )
    private val indexName = IndexName("productos")

    fun getIndex(): Index = client.initIndex(indexName)
}