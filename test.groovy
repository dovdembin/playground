@Library("shared-library") _
 
 

stage("Running Stage: ") {


    def map = [endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT, counter:"tridevlab.test-counter"]
    log.meterCounter(map);
}