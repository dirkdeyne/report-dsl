package be.enyed.report.dsl

import be.enyed.report.*
import java.time.LocalDateTime

fun reprt(init: ReportBuilder.() -> Unit ): Report {
    val reportBuilder = ReportBuilder().apply(init)
    return reportBuilder.build()
}

class ReportBuilder {
    lateinit var metadata:MetaData
    var data:MutableSet<Data> = mutableSetOf<Data>()
    var output = mutableSetOf<Output>()
    var parameters = mutableSetOf<Parameter>()

    fun meta(block: MetaDataBuilder.() -> Unit){
        metadata = MetaDataBuilder().apply(block).build()
    }

    fun data(block: DATASET.() -> Unit){
        data.addAll(DATASET().apply(block))
    }

    fun build():Report {
        return Report(metadata, parameters,data, output)
    }
}

class DATASET:HashSet<Data>(){
    fun db(name:String, block: DataBuilder.() -> Unit){
        var db = DataBuilder()
        add(db.build())
    }
}

class SELECTSET:HashSet<Select>(){
    fun db(name:String, block: SelectBuilder.() -> Unit){
        var select = SelectBuilder()
        add(select.build())
    }
}

class MetaDataBuilder {

    var reportName:String = "reprt"
    val creationDate: LocalDateTime =  LocalDateTime.now()
    var version: String = "1.0.0"
    var faze = Faze.TEST
    var online:Boolean = false

    fun build():MetaData {
        return MetaData(reportName,creationDate,Version(version), faze ,online)
    }
}

fun Data.select(block:SelectBuilder.()-> Unit):Select {
    return SelectBuilder().apply(block).build()
}

class DataBuilder {
    var selects = mutableSetOf<Select>()

    fun select(block: SELECTSET.() -> Unit){
        selects.addAll(SELECTSET().apply(block))
    }

    fun build(): Data {
        val data = Data(selects)
        return data
    }
}

class SelectBuilder {
    var name = "select"
    var select = "select"
    var dataStore = DataStore("","","")

    fun build(): Select {
        val data = Select(dataStore,name,select)
        return data
    }
}