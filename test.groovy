@Library("shared-library") _
 
 
 try {
    stage("Running Stage: ") {
        log.otelcli(
            endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
            service_name:"otel-cli-java"
            )
    }
} catch(Exception e) {

}