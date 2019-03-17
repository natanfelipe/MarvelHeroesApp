package com.natanbrito.marvelheroesapp.model

data class Data(val results: List<Characters>, val offset: Int, val limit: Int, val total: Int, val count: Int)