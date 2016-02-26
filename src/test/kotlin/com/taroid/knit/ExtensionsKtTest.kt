package com.taroid.knit

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.sameInstance
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(Enclosed::class)
class ExtensionsKtTest {

    class `should for T` {

        @Test
        fun `should be alias for AssertFactory#newInstance`() {
            val target = Any()
            val origin = AsserterFactory.newInstance(target)
            val alias = target.should

            assertThat(alias.javaClass, `is`(sameInstance(origin.javaClass)))
            assertThat(alias.target, `is`(sameInstance(origin.target)))
        }
    }

    class `should for lazy T` {

        @Test
        fun `should return Asserter which equals what T#should returns`() {
            val target = Any()
            val expected = target.should
            val got = { target }.should

            assertThat(got.javaClass, `is`(sameInstance(expected.javaClass)))
            assertThat(got.target, `is`(sameInstance(expected.target)))
        }
    }

    class `returns - value` {

        @Test
        fun `return the expected value when mocked function is called`() {
            @Suppress("UNCHECKED_CAST")
            val mockAsserter = Mockito.mock(Asserter::class.java) as Asserter<String?>

            mockAsserter.target returns null
            assertThat(mockAsserter.target, `is`(null as String?))

            mockAsserter.target returns "Kotlin"
            assertThat(mockAsserter.target, `is`("Kotlin"))
        }
    }

    class `returns - values` {

        @Test
        fun `return the expected values when mocked function is called`() {
            @Suppress("UNCHECKED_CAST")
            val mockAsserter = Mockito.mock(Asserter::class.java) as Asserter<String?>

            mockAsserter.target.returns(null, "Kotlin")
            assertThat(mockAsserter.target, `is`(null as String?))
            assertThat(mockAsserter.target, `is`("Kotlin"))
        }
    }

    class `nextReturns - value` {

        @Test
        fun `return the expected value when mocked function is called for the second time`() {
            @Suppress("UNCHECKED_CAST")
            val mockAsserter = Mockito.mock(Asserter::class.java) as Asserter<String?>

            mockAsserter.target returns null nextReturns "Kotlin"
            assertThat(mockAsserter.target, `is`(null as String?))
            assertThat(mockAsserter.target, `is`("Kotlin"))
        }
    }

    class `throws - throwable` {

        @Test(expected = NullPointerException::class)
        fun `throws error when mocked function is called`() {
            @Suppress("UNCHECKED_CAST")
            val mockAsserter = Mockito.mock(Asserter::class.java) as Asserter<String?>

            mockAsserter.target throws NullPointerException()
            mockAsserter.target
        }
    }

    class `throws - throwables` {

        @Test(expected = UnsupportedOperationException::class)
        fun `throws errors when mocked function is called`() {
            @Suppress("UNCHECKED_CAST")
            val mockAsserter = Mockito.mock(Asserter::class.java) as Asserter<String?>

            mockAsserter.target.throws(NullPointerException(), UnsupportedOperationException())
            try {
                mockAsserter.target
            } catch (e: NullPointerException) {
                // Nothing to do.
            }
            mockAsserter.target
        }
    }

    class `nextThrows - throwable` {

        @Test(expected = UnsupportedOperationException::class)
        fun `throws error when mocked function is called`() {
            @Suppress("UNCHECKED_CAST")
            val mockAsserter = Mockito.mock(Asserter::class.java) as Asserter<String?>

            mockAsserter.target throws NullPointerException() nextThrows UnsupportedOperationException()
            try {
                mockAsserter.target
            } catch (e: NullPointerException) {
                // Nothing to do.
            }
            mockAsserter.target
        }
    }
}
