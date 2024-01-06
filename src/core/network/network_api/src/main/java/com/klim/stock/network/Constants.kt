package com.klim.stock.network

object Constants {
    const val SUBDOMAIN_KEY = "subdomain"
    const val PATH_SUBDOMAIN = "{${SUBDOMAIN_KEY}}"
    const val SERVER_URL = "https://${PATH_SUBDOMAIN}finance.yahoo.com/"
    const val SUBDOMAIN_QUERY1 = "query1."
    const val SUBDOMAIN_QUERY2 = "query2."
}