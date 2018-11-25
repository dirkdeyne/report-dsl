package be.enyed.report

import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity data class Data(@OneToMany val selects:Set<Select>, @Id @GeneratedValue val id:Long=1)
@Entity data class DataStore(val name:String, val user:String = "sa", val password: String, @Id @GeneratedValue val id:Long=1)
@Entity data class Select(val dataStore: DataStore, val name:String, val select:String, @Id @GeneratedValue val id:Long=1)
@Entity data class Parameter(val key:String, val value:Any, val required:Boolean, val hidden:Boolean, @Id @GeneratedValue val id:Long=1)
interface DataStoreRepository : CrudRepository<DataStore,Long> {
    fun findByName(name: String) : DataStore
}

interface ReportService {
    fun findByName(name: String) : DataStore
}

class ReportServiceJpa(val dataStoreRepository: DataStoreRepository) : ReportService {

  override fun findByName(name: String) = dataStoreRepository.findByName(name)

}