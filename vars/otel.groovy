
@Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.23.1', type='pom')
@Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.23.1')
@Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.23.1-alpha', scope='runtime')
@Grab(group='io.opentelemetry', module='opentelemetry-sdk-extension-autoconfigure', version='1.23.1-alpha')
@Grab(group='io.opentelemetry', module='opentelemetry-sdk-extension-autoconfigure-spi', version='1.23.1')

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;

import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
 
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.resources.ResourceBuilder;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;



@NonCPS
def meterCounter(Map config = [:]) {
	Map<String, String> configurationProperties = new HashMap<>();
		configurationProperties.put("bpt-trident", "1.0.0");

		OpenTelemetryConfiguration openTelemetryConfiguration = new OpenTelemetryConfiguration(
				Optional.of("http://172.31.64.1:4317/"), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.ofNullable("tridevlab"), Optional.ofNullable(""), Optional.empty(), configurationProperties);

		OpenTelemetrySdkProvider openTelemetrySdkProvider = new OpenTelemetrySdkProvider();
		openTelemetrySdkProvider.initialize(openTelemetryConfiguration);

		

		// Build counter e.g. LongCounter
		LongCounter counter = openTelemetrySdkProvider.meter.counterBuilder("tridevlab.test-counter")
				.setDescription("tridevlab.test-counter").setUnit("1").build();

		Attributes attributes = Attributes.of(AttributeKey.stringKey("Key"), "SomeWork", AttributeKey.stringKey("Key2"),
				"SomeWork2", AttributeKey.stringKey("Key3"), "SomeWork3", AttributeKey.stringKey("Key4"), "SomeWork4");

		counter.add(1, attributes);

		openTelemetrySdkProvider.shutdown();

}



class OpenTelemetryConfiguration {

	private final Optional<String> endpoint;
	private final Optional<String> trustedCertificatesPem;
	private final Optional<Integer> exporterTimeoutMillis;
	private final Optional<Integer> exporterIntervalMillis;
	private final Optional<String> serviceName;
	private final Optional<String> serviceNamespace;
	private final Optional<String> disabledResourceProviders;
	private final Map<String, String> configurationProperties;

	public OpenTelemetryConfiguration(Optional<String> endpoint, Optional<String> trustedCertificatesPem,
			Optional<Integer> exporterTimeoutMillis, Optional<Integer> exporterIntervalMillis,
			Optional<String> serviceName, Optional<String> serviceNamespace, Optional<String> disabledResourceProviders,
			Map<String, String> configurationProperties) {
		this.endpoint = endpoint;
		this.trustedCertificatesPem = trustedCertificatesPem;
		this.exporterTimeoutMillis = exporterTimeoutMillis;
		this.exporterIntervalMillis = exporterIntervalMillis;
		this.serviceName = serviceName;
		this.serviceNamespace = serviceNamespace;
		this.disabledResourceProviders = disabledResourceProviders;
		this.configurationProperties = configurationProperties;
	}

	public Optional<String> getEndpoint() {
		return endpoint;
	}

	public Optional<String> getServiceName() {
		return serviceName;
	}

	public Optional<String> getServiceNamespace() {
		return serviceNamespace;
	}

	public Optional<String> getTrustedCertificatesPem() {
		return trustedCertificatesPem;
	}

	public Optional<Integer> getExporterTimeoutMillis() {
		return exporterTimeoutMillis;
	}

	public Optional<Integer> getExporterIntervalMillis() {
		return exporterIntervalMillis;
	}

	public Optional<String> getDisabledResourceProviders() {
		return disabledResourceProviders;
	}

	public Map<String, String> toOpenTelemetryProperties() {
		Map<String, String> properties = new HashMap<>();
		properties.putAll(this.configurationProperties);

		this.getEndpoint().ifPresent{endpoint -> 
			properties.put("otel.traces.exporter", "otlp");
			properties.put("otel.metrics.exporter", "otlp");
			properties.put("otel.exporter.otlp.endpoint", endpoint);
		};

		
		
		return properties;
	}

	public Resource toOpenTelemetryResource() {
		ResourceBuilder resourceBuilder = Resource.builder();
		resourceBuilder.put("bpt", "1.0.0");
		return resourceBuilder.build();
	}

}

class OpenTelemetrySdkProvider {

	 

	public OpenTelemetry openTelemetry;
	public OpenTelemetrySdk openTelemetrySdk;

	
	public Meter meter;
	public Resource resource;
	public ConfigProperties config;
	

	public OpenTelemetrySdkProvider() {
	}

	

	public Meter getMeter() {
		return meter;
	}

	public Resource getResource() {
		return resource;
	}

	public ConfigProperties getConfig() {
		return config;
	}

	

	

	

	public OpenTelemetrySdk getOpenTelemetrySdk() {
		return openTelemetrySdk;
	}

	public void shutdown() {
		if (this.openTelemetrySdk != null) {
			
			this.openTelemetrySdk.getSdkMeterProvider().shutdown();
			
		}
		GlobalOpenTelemetry.resetForTest();
		
	}

	public void initialize(OpenTelemetryConfiguration configuration) {
		shutdown();
		initializeOtlp(configuration);
	}

	public void initializeOtlp(OpenTelemetryConfiguration configuration) {

		AutoConfiguredOpenTelemetrySdkBuilder sdkBuilder = AutoConfiguredOpenTelemetrySdk.builder();
		Map<String, String> properties = new HashMap<>();
		properties.put("otel.traces.exporter", "otlp");
		sdkBuilder.addPropertiesSupplier(properties);
		// PROPERTIES
		// sdkBuilder.addPropertiesSupplier{s -> configuration.toOpenTelemetryProperties()};

		// RESOURCE
		// sdkBuilder.addResourceCustomizer{resource, configProperties -> 
		// 	ResourceBuilder resourceBuilder = Resource.builder().putAll(resource)
		// 			.putAll(configuration.toOpenTelemetryResource());
		// 	return resourceBuilder.build();
		// };

		
		AutoConfiguredOpenTelemetrySdk autoConfiguredOpenTelemetrySdk = sdkBuilder.build();
		this.openTelemetrySdk = autoConfiguredOpenTelemetrySdk.getOpenTelemetrySdk();
		this.resource = autoConfiguredOpenTelemetrySdk.getResource();
		this.config = autoConfiguredOpenTelemetrySdk.getConfig();
		this.openTelemetry = this.openTelemetrySdk;
		
		this.meter = openTelemetry.getMeterProvider().get("bpt-meter");

	}


}
