package be.enyed.report

import org.jetbrains.kotlin.daemon.nowSeconds
import java.time.LocalDateTime
import javax.persistence.*

@Entity data class Report(@Embedded val meta:MetaData, @OneToMany val parameters: Set<Parameter>,@OneToMany val data:Set<Data>, @OneToMany val outputs:Set<Output>, @Id val id:Long = nowSeconds())

@Embeddable class MetaData(val reportName: String, val creationDate: LocalDateTime, val version: Version, val faze:Faze, val online:Boolean){
    override fun toString() = "$reportName-$version"
}

class Version(val name:String) {
    override fun toString(): String {
        return name
    }
}

enum class Faze {
    TEST,RELEASE,ARCHIVE
}