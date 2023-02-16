@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.otelcli(env.OTEL_EXPORTER_OTLP_ENDPOINT,"otel-cli-java",va:"koko")
}