package be.enyed.report.dsl

import be.enyed.report.*
import java.time.LocalDateTime

@DslMarker
annotation class ReportDsl

class ReportDslContext {
    companion object {
        lateinit var reportService: ReportService
    }
}

fun report(init:ReportDslBuilder.() -> Unit):Report {
    return ReportDslBuilder().apply(init).build();
}

fun ReportDslBuilder.metadata(init:MetadataDslBuilder.() -> Unit) {
    metadata = MetadataDslBuilder(this).apply(init).build();
}

fun ReportDslBuilder.parameters(init:ParameterDslBuilder.() -> Unit){
    parameters.add(ParameterDslBuilder(this).apply(init).build())
}

fun ReportDslBuilder.data(init:DataDslBuilder.() -> Unit){
    var map = parameters.associateByTo(mutableMapOf<String,Any>(),{it.key},{it.value})
    data.add(DataDslBuilder(map).apply(init).build())
}

fun DataDslBuilder.select(init:SelectDslBuilder.()-> Unit){
    selects.add(SelectDslBuilder(this).apply(init).build())
}

fun ReportDslBuilder.outputs(init:OutputDslBuilder.() -> Unit){
    outputs.add(OutputDslBuilder(this).apply(init).build())
}

@ReportDsl
class ReportDslBuilder {
    lateinit var metadata:MetaData
    var data = mutableSetOf<Data>()
    var outputs = mutableSetOf<Output>()
    var parameters = mutableSetOf<Parameter>()
    fun build():Report {
        return Report(metadata,parameters,data,outputs)
    }
}

@ReportDsl
class MetadataDslBuilder (val report:ReportDslBuilder) {
    private val creationDate =  LocalDateTime.now()
    var reportName = "reprt"
    var version = "1.0.0"
    var faze = Faze.TEST
    var online = false

    fun build():MetaData {
        return MetaData(reportName,creationDate,Version(version), faze, online)
    }
}

@ReportDsl
class ParameterDslBuilder (val report:ReportDslBuilder) {

    lateinit  var parameter:Parameter

    fun optional(key:String, value:String="", hidden:Boolean = false){
        parameter = Parameter(key,value,false, hidden)
    }

    fun required(key:String, value:String="", hidden:Boolean = false){
        parameter = Parameter(key,value,true,hidden)
    }

    fun hidden(key:String, value:String="",required:Boolean = false){
        parameter = Parameter(key,value,required,false)
    }

    fun build():Parameter {
        return parameter
    }
}

@ReportDsl
class DataDslBuilder (val parameters:Map<String,Any>) {
    var selects = mutableSetOf<Select>()

    fun build():Data {
        return Data(selects)
    }
}

@ReportDsl
class SelectDslBuilder (val data:DataDslBuilder) {
    var name:String = ""
    var select:String = ""
    var dataStore:String = ""

    val store by lazy { ReportDslContext.reportService.findByName(dataStore)   }

    inline fun p(key:String) = data.parameters[key]

    fun build():Select {
        return Select(store, name, select)
    }
}

@ReportDsl
class OutputDslBuilder (val report:ReportDslBuilder) {
    var name = ""
    fun html()= ""
    fun pivot()= ""
    fun build():Output {
        return Output( name )
    }
}