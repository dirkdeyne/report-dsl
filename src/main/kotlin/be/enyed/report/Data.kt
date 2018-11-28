package be.enyed.report

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity data class Data(@OneToMany val selects:Set<Select>, @Id @GeneratedValue val id:Long=1)
@Entity data class DataStore(val alias:String, val name:String, val type : Type, @OneToMany  val parameters:Set<Pair<String,String>> = emptySet() ,@Id @GeneratedValue val id:Long=1)
@Entity data class Select(val dataStore: DataStore, val name:String, val select:String, @Id @GeneratedValue val id:Long=1)
@Entity data class Parameter(val key:String, val value:Any, val required:Boolean, val hidden:Boolean, @Id @GeneratedValue val id:Long=1)

interface ReportService {
    fun findByName(name: String) : DataStore
}

interface DataCollector {
    fun collect(store: DataStore, select: String): Any
}

enum class Type {
    BEAN, JNDI, JDBC, MOCK, SHP, SAMBA
}