package be.enyed.report

import java.time.LocalDateTime
import javax.persistence.*

@Entity data class Report(@Embedded val meta:MetaData, @OneToMany val parameters: Set<Parameter>,@OneToMany val data:Set<Data>, @OneToMany val outputs:Set<Output>, @Id @GeneratedValue val id:Long = 1L)

@Embeddable class MetaData(val reportName: String, val creationDate: LocalDateTime, @Embedded val version: Version, @Enumerated(EnumType.STRING) val faze:Faze, val online:Boolean){
    override fun toString() = "$reportName-$version"
}

@Embeddable class Version(val name:String) {
    override fun toString(): String {
        return name
    }
}

enum class Faze {
    DEVELOPMENT,TEST,RELEASE,ARCHIVE
}