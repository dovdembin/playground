
@Grab(group='io.grpc', module='grpc-stub', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.23.1', type='pom')
@Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.23.1-alpha', scope='runtime')


import java.util.concurrent.TimeUnit;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
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

def meterCounter(Map config = [:]) {

	Resource resource = Resource.getDefault()
			.merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "otel-cli-java")));

	OtlpGrpcMetricExporter metricOtlpExporter =	OtlpGrpcMetricExporter.builder()
			.setEndpoint(config.endpoint)
			.setTimeout(30, TimeUnit.SECONDS)
			.build();
	
	SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
			.registerMetricReader(PeriodicMetricReader.builder(metricOtlpExporter).build())
			.setResource(resource).build();

	OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
			.setMeterProvider(sdkMeterProvider)
			.setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
			.buildAndRegisterGlobal();
	
	Meter meter = openTelemetry.meterBuilder("instrumentation-library-name")
			.setInstrumentationVersion("1.0.0")
			.build();

	LongCounter counter = meter
				.counterBuilder(config.counter)
				.setDescription(config.counter)
				.setUnit("1")
				.build();

	config.remove("endpoint");
	config.remove("counter");
	AttributesBuilder attr = Attributes.builder();
	for (entry in config) {
		attr.put(entry.getKey(), entry.getValue());
	}
	counter.add(1, attr.build());
	metricOtlpExporter.close();	

}