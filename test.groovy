@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.otelcli(
        endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
        servicename:"otel-cli-java2"
        )
}