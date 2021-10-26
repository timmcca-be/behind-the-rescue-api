package com.adoptastray.behindtherescue.domain.cratestacks

data class CrateStack(val bottom: Crate, val top: Crate? = null) : Comparable<CrateStack> {
    override fun compareTo(other: CrateStack): Int {
        val bottomCompare = bottom.compareTo(other.bottom)
        return if (bottomCompare != 0) {
            bottomCompare
        } else if (top == null && other.top == null) {
            0
        } else if (top == null) {
            1
        } else if (other.top == null) {
            -1
        } else {
            top.compareTo(other.top)
        }
    }
}