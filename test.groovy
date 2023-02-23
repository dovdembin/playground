@Library("shared-library") _
 

def countTestOTel() {
    dir ("D://"){
 // Execute your java file
 echo "koko"
 }
}

stage("Running Stage: ") {



dir("utils") {
    countTestOTel()
    //    sh(script: """
    //     java -jar --counter="tridevlab.test-counter" --endpoint=env.OTEL_EXPORTER_OTLP_ENDPOINT -a "test.name"="aa"
    // """, label: "Report OTel", returnStatus: true)
    // }

     
}