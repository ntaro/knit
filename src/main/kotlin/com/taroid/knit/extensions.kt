package com.taroid.knit

import org.hamcrest.Matcher
import org.junit.Assert
import org.mockito.Mockito.`when`
import org.mockito.stubbing.OngoingStubbing

val <T> T.should: Asserter<T>
    get() = AsserterFactory.newInstance(this)

val <T> (() -> T).should: Asserter<T>
    get() = this().should

fun <T> T.should(matcher: Matcher<in T>) {
    Assert.assertThat(this, matcher)
}

infix fun <T> T.returns(value: T) = `when`(this).thenReturn(value)

fun <T> T.returns(value: T, vararg values: T) = `when`(this).thenReturn(value, *values)

infix fun <T> OngoingStubbing<T>.nextReturns(value: T) = this.thenReturn(value)

infix fun <T> T.throws(throwable: Throwable) = `when`(this).thenThrow(throwable)

fun <T> T.throws(throwable: Throwable, vararg throwables: Throwable) =
        `when`(this).thenThrow(throwable, *throwables)

infix fun <T> OngoingStubbing<T>.nextThrows(throwable: Throwable) = this.thenThrow(throwable)
