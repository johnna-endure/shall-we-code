package com.shallwecode.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * 주어진 숫자 배열이 내림차순인지 확인합니다.
 */
fun isDescending(numbers: List<Long>): Boolean {
    if (numbers.isEmpty()) throw IllegalArgumentException("numbers is empty")

    fun check(idx: Int, numbers: List<Long>): Boolean {
        if (idx >= numbers.size - 1) return true
        val target = numbers[idx]
        val compare = numbers.subList(1, numbers.size).maxOrNull() ?: 0

        val targetIsBigger = target >= compare
        return targetIsBigger && check(idx + 1, numbers.subList(1, numbers.size))
    }

    return check(0, numbers)
}


class TestUtilFunctions {
    @Test
    fun `isDescendingTest - 내림차순 리스트가 주어진 경우`() {
        // given
        val list = listOf<Long>(3, 2, 1)

        // then
        assertThat(isDescending(list)).isTrue
    }

    @Test
    fun `isDescendingTest - 내림차순이 아닌 리스트가 주어진 경우`() {
        // given
        val list = listOf<Long>(3, 5, 1)

        // then
        assertThat(isDescending(list)).isFalse
    }

    @Test
    fun `isDescendingTest - 내림차순이지만 같은 숫자가 주어진 경우`() {
        // given
        val list = listOf<Long>(3, 3, 1)

        // then
        assertThat(isDescending(list)).isTrue
    }

    @Test
    fun `isDescendingTest - 배열의 길이가 1,2인 경우`() {
        // 배열의 길이가 2인 경우
        // given
        val list2 = listOf<Long>(3, 1)

        // then
        assertThat(isDescending(list2)).isTrue

        // 배열의 길이가 1인 경우
        // given
        val list1 = listOf<Long>(1)

        // then
        assertThat(isDescending(list1)).isTrue
    }

    @Test
    fun `isDescendingTest - 배열의 길이가 0인 경우`() {
        // given
        val list = listOf<Long>()

        // then
        assertThrows<IllegalArgumentException> { isDescending(list) }
    }
}
