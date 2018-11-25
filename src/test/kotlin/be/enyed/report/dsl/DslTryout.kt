package be.enyed.report.dsl

/**
 * DSL tryout
**/
data class A(val name:String, val b:B, val e:E )
data class B(val name:String, val c:Set<C> )
data class C(val name:String, val d:Set<D> )
data class D(val name:String ="d")
data class E(val name:String ="e")

@DslMarker
annotation class ADsl

@ADsl
class ABuilder() {
    var name = "a"
    lateinit var b:B
    lateinit var e:E
    fun build()= A(name,b,e)
}

@ADsl
class BBuilder() {
    var name = "b"
    var c = mutableSetOf<C>()
    fun build()= B(name,c)
}

@ADsl
class CBuilder() {
    var name = "c"
    var d = mutableSetOf<D>()
    fun build()= C(name,d)
}

@ADsl
class DBuilder() {
    var name = "d"
    fun build()= D(name)
}

@ADsl
class EBuilder() {
    var name = "e"
    fun build()= E(name)
}

fun a(init:ABuilder.() -> Unit) = ABuilder().apply(init).build()

fun ABuilder.b(init:BBuilder.() -> Unit){
    b = BBuilder().apply(init).build()
}

fun ABuilder.e(init:EBuilder.() -> Unit){
    e = EBuilder().apply(init).build()
}

fun BBuilder.c(init:CBuilder.() -> Unit){
    c.add(CBuilder().apply(init).build())
}

fun CBuilder.d(init:DBuilder.() -> Unit){
    d.add(DBuilder().apply(init).build())
}

fun main(args: Array<String>) {
    println(

            a {
                name = "hello A"
                b {
                    name= "hello B"
                    c {
                        name="hello C1"
                        d {
                            name ="hello D1"
                        }
                        d {
                            name ="hello D2"
                        }
                    }
                    c {
                        name="hello C2"
                        d {
                            name ="hello D3"
                        }
                    }
                }
                e {
                    name= "hello E"
                }
            }

    )
}