// !DUMP_CFG
import kotlin.contracts.*

fun bar(x: () -> Unit) {

}

@ExperimentalContracts
fun foo(x: () -> Unit) {
    contract {
        callsInPlace(x, InvocationKind.AT_MOST_ONCE)
    }

    x()

    object : Runnable {
        override fun run() {
            x()
        }
    }.run()

    val baz = {
        x()
    }

    bar {
        x()
    }
}
