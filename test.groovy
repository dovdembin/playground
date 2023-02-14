def countTestOTel() {
    openTelemetry = ExampleConfiguration.initOpenTelemetry("http://localhost:4317");
    tracer = openTelemetry.getTracer("io.opentelemetry.example");
    exampleSpan = tracer.spanBuilder("oteljenkins").startSpan();
    exampleSpan.setAttribute("koko", "loko");
    exampleSpan.end();
}

try {
 countTestOTel()
} catch (Exception e) {
         
    }