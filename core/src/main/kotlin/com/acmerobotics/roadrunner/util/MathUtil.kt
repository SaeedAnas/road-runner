package com.acmerobotics.roadrunner.util

import kotlin.math.abs
import kotlin.math.sqrt

object MathUtil {
    fun solveQuadratic(a: Double, b: Double, c: Double): List<Double> {
        val disc = b * b - 4 * a * c
        return when {
            abs(disc) < 1e-6 -> listOf(-b / (2 * a))
            disc > 0.0 -> listOf(
                    (-b + sqrt(disc)) / (2 * a),
                    (-b - sqrt(disc)) / (2 * a)
            )
            else -> emptyList()
        }
    }
}