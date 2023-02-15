def countTestOTel() {
    openTelemetry = ExamplesConfiguration.initOpenTelemetry("http://localhost:4317");
    tracer = openTelemetry.getTracer("io.opentelemetry.example");
    exampleSpan = tracer.spanBuilder("oteljenkins").startSpan();
    scope = exampleSpan.makeCurrent() 
    exampleSpan.setAttribute("good", "true");
    exampleSpan.setAttribute("exampleNumber", i);
    Thread.sleep(100);    
    exampleSpan.end();   
}

try {
 countTestOTel()
} catch (Exception e) {
         
    }