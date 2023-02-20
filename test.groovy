@Library("shared-library") _
 
 

stage("Running Stage: ") {


    def map = [endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT, likes: counter:"tridevlab.test-counter", test.name:"aa"]
    log.meterCounter(map);
}