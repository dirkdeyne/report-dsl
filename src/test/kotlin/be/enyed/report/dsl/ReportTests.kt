package be.enyed.report.dsl

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReportTests{

    @Test
    fun `basic report build`(){
        val report =

                reprt {
                    meta {
                        reportName = "basic reprt"
                    }
                    data{
                        db("data") {
                            select {  }
                            select {  }
                        }
                        db("other") {
                            select {  }
                            select {  }
                        }
                    }
                }

        println("reprt =  $report")

        Assertions.assertThat(report.meta.reportName).isEqualTo("basic reprt")
    }
}