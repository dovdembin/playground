@Library("shared-library") _
 
 

stage("Running Stage: ") {
    Otel otel = new Otel(endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT, counter:"tridevlab.test-counter");
}