package be.enyed.report

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity data class Output(val output:String, @Id @GeneratedValue val id:Long = 1L )