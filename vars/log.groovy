

@Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.23.1', type='pom')
@Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.23.1-alpha', scope='runtime')
@Grab(group='io.grpc', module='grpc-bom', version='1.53.0', type='pom')
@Grab(group='io.grpc', module='grpc-stub', version='1.53.0')



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
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.common.AttributesBuilder;

def meterCounter(Map config = [:]) {
GroovyClassLoader gos = new GroovyClassLoader();
gos.loadClass("io.grpc.ManagedChannel");
	try {
	Resource resource = Resource.getDefault()
  .merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "logical-service-name")));



SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
  .registerMetricReader(PeriodicMetricReader.builder(OtlpGrpcMetricExporter.builder().setEndpoint(config.endpoint).build()).build())
  .setResource(resource)
  .build();

OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()

  .setMeterProvider(sdkMeterProvider)
  .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
  .buildAndRegisterGlobal();

	Meter meter = openTelemetrySdk.meterBuilder("instrumentation-library-name")
			.setInstrumentationVersion("1.0.0")
			.build();

	LongCounter counter = meter
			.counterBuilder(config.counter)
			.setDescription(config.counter)
			.setUnit("1")
			.build();
	AttributesBuilder attr = Attributes.builder();
	for (entry in config) {
		attr.put(entry.key, entry.value);
	}
	counter.add(1, attr.build());
	openTelemetrySdk.getSdkMeterProvider().close();
	} catch ( e ) {
     
}
}
