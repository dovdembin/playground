@Grapes([
   @Grab(group='io.opentelemetry', module='opentelemetry-bom', version='1.20.1', type='pom'),
   @Grab(group='io.opentelemetry', module='opentelemetry-api', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-sdk', version='1.23.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-exporter-otlp', version='1.22.0'),
   @Grab(group='io.opentelemetry', module='opentelemetry-semconv', version='1.20.1-alpha', scope='runtime')
])
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
Tracer tracer = openTelemetry.getTracer("io.opentelemetry.example");
pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                
                // Get some code from a GitHub repository
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
