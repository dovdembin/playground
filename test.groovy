@Grapes([
   @Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.20.1', type='pom'),
   @Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.22.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.20.1-alpha', scope='runtime')
])


def countTestOTel(duration, end_status) {
     
}

try {
 countTestOTel(duration, end_status)
} catch (Exception e) {
         
    }