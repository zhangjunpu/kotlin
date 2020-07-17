// !DUMP_CFG
import kotlin.contracts.*

fun bar(x: () -> Unit) {

}

@ExperimentalContracts
fun foo(x: () -> Unit, y: () -> Unit, z: () -> Unit) {
    <!WRONG_INVOCATION_KIND, WRONG_INVOCATION_KIND, WRONG_INVOCATION_KIND!>contract {
        callsInPlace(x, InvocationKind.EXACTLY_ONCE)
        callsInPlace(y, InvocationKind.EXACTLY_ONCE)
        callsInPlace(z, InvocationKind.EXACTLY_ONCE)
    }<!>

    if (true) {
        bar(x)
    } else {
        val yCopy = y
        yCopy()
    }

    val zCopy: () -> Unit
    zCopy = z
    zCopy()
}