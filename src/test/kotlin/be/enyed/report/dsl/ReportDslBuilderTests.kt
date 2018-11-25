package be.enyed.report.dsl

import be.enyed.report.DataStore
import be.enyed.report.Faze
import be.enyed.report.ReportService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReportDslBuilderTests {

    @BeforeAll
    fun setup(){
        ReportDslContext.reportService = TestReportService()
    }

    @Test
    fun `build a basic report`() {

        println(

            report {
                metadata {
                    reportName = "test"
                    version = "0.0.1.TST"
                    faze = Faze.TEST
                    online = true
                }

                parameters {
                    required("name")
                    optional("faze" ,"RELEASE" )
                    hidden("user", "dirk")
                }

                data {
                    select {
                        name = "tst01"
                        dataStore = "test01"
                        select = """select * from test_table_01 where user = ${p("user")}"""
                    }

                    select {
                        name = "tst02"
                        dataStore = "test02"
                        select = "select * from test_table_02"
                    }
                }

                outputs {
                    html()
                    pivot()
                }
            }

        )
    }

    class TestReportService : ReportService {
        override fun findByName(name: String): DataStore =
            DataStore("test data store" ,"usr", "*****")

    }
}