@Grapes([
   @Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.20.1', type='pom'),
   @Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.22.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.20.1-alpha', scope='runtime')
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

public class ExampleConfiguration {

	static OpenTelemetry initOpenTelemetry(String jaegerEndpoint) {
		// Export traces to Jaeger over OTLP
	    OtlpGrpcSpanExporter jaegerOtlpExporter =
	        OtlpGrpcSpanExporter.builder()
	            .setEndpoint(jaegerEndpoint)
	            .setTimeout(30, TimeUnit.SECONDS)
	            .build();
	    
	    OtlpGrpcMetricExporter metricOtlp =
	    		OtlpGrpcMetricExporter.builder()
		            .setEndpoint(jaegerEndpoint)
		            .setTimeout(30, TimeUnit.SECONDS)
		            .build();
	    
		Resource resource = Resource.getDefault().merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "service3")));

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

def func(endpoint){
    OpenTelemetry openTelemetry = ExampleConfiguration.initOpenTelemetry(endpoint);
     
    Tracer tracer = openTelemetry.getTracer("io.opentelemetry.example");
    Span exampleSpan = tracer.spanBuilder("oteljenkins").startSpan();
    exampleSpan.setAttribute("good", "true");
    exampleSpan.end();
}


