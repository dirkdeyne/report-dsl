package be.enyed.report

import javax.persistence.*

@Entity data class Data(@OneToMany  @JoinColumn(name="SELECT_ID") val selects:Set<Select>, @Id @GeneratedValue val id:Long=1)
@Entity data class DataStore(val alias: String, val name: String, @Enumerated(EnumType.STRING) val type: Type, @OneToMany @JoinColumn(name="PARAM_ID") val parameters: Set<NameValue> = emptySet(), @Id @GeneratedValue val id:Long=1)
@Entity data class Select(@ManyToOne @JoinColumn(name="DATA_STORE_ID") val dataStore: DataStore, val name: String, val select: String, @Id @GeneratedValue val id: Long=1)
@Entity data class Parameter(val key: String, val value: String, val required:Boolean, val hidden: Boolean, @Id @GeneratedValue val id: Long=1)
@Entity data class NameValue(val name: String, val value: String, @Id @GeneratedValue val id: Long = 1L)

interface ReportService {
    fun findByName(name: String) : DataStore
}

interface DataCollector {
    fun collect(store: DataStore, select: String): Any  // JSON, Map, ... ?
}

enum class Type {
    BEAN, JNDI, JDBC, MOCK, SHP, SAMBA
}