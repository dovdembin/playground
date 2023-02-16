@Library("shared-library") _
 
 
 try {
    stage("Running Stage: ") {
        log.otelCli(endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT)
    }
} catch(Exception e) {

}