@Grapes([
   @Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.20.1', type='pom'),
   @Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.22.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.20.1-alpha', scope='runtime'),
   @Grab(group='io.grpc', module='grpc-stub', version='1.53.0')
])

import java.util.concurrent.TimeUnit;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.grpc.*

public class ExampleConfiguration {

	static OpenTelemetry initOpenTelemetry(Map config = [:]) {
		// Export traces to Jaeger over OTLP
	    OtlpGrpcSpanExporter jaegerOtlpExporter =
	        OtlpGrpcSpanExporter.builder()
	            .setEndpoint(config.jaegerEndpoint)
	            .setTimeout(30, TimeUnit.SECONDS)
	            .build();
	    
	    OtlpGrpcMetricExporter metricOtlp =
	    		OtlpGrpcMetricExporter.builder()
		            .setEndpoint(config.jaegerEndpoint)
		            .setTimeout(30, TimeUnit.SECONDS)
		            .build();
	    
		Resource resource = Resource.getDefault().merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, config.servicename)));

				SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
				  .addSpanProcessor(BatchSpanProcessor.builder(jaegerOtlpExporter).build())
				  .setResource(resource)
				  .build();

				SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
				  .registerMetricReader(PeriodicMetricReader.builder(metricOtlp).build())
				  .setResource(resource)
				  .build();

				OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
				  .setTracerProvider(sdkTracerProvider)
				  .setMeterProvider(sdkMeterProvider)
				  .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
				  .buildAndRegisterGlobal();
		return openTelemetry;
	}
}

def otelcli(Map config = [:]){
    println("this is endpoint: ${config.endpoint} this is ${config.servicename}")
    OpenTelemetry openTelemetry = ExampleConfiguration.initOpenTelemetry(jaegerEndpoint:config.endpoint, servicename:config.servicename);

    Tracer tracer = openTelemetry.getTracer("scope");
	Meter meter = openTelemetry.getMeter("scope");
	LongCounter counter = meter.counterBuilder("example_counter").build();
	LongHistogram histogram = meter.histogramBuilder("super_timer").ofLongs().setUnit("ms").build();

    for (int i = 0; i < 25; i++) {
		long startTime = System.currentTimeMillis();
	    Span exampleSpan = tracer.spanBuilder("otel-jenkins").startSpan();
		Scope scope = exampleSpan.makeCurrent();
		try {
			counter.add(1);
			exampleSpan.addEvent("Event 0");
			exampleSpan.setAttribute("good", "true");
			Thread.sleep(100);
		} finally {
        	histogram.record(System.currentTimeMillis() - startTime);
			scope.close();
        	exampleSpan.end();
			println("closed all")
        }
	}
      
}


