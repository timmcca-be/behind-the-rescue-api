package com.adoptastray.behindtherescue.application.common.exception

data class EntityNotFoundException(val model: String, val id: Int) : RuntimeException("Could not find ${model} with ID ${id}") {
}