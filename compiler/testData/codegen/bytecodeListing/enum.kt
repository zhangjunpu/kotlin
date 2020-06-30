enum class SimpleEnum {
    A, B, C
}

enum class WithConstructor(val x: String) {
    A("1"), B("2"), C("3")
}

enum class WithEntryClass {
    A {
        override fun foo() {}
    }
    ;
    abstract fun foo()
}