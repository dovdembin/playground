@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.meterCounter(
        endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
        counter:"tridevlab.test-counter",
        koko:"loko", 
        k44:"bb",
        barba:"papa",
        k1:"k11",
        k2:"k22",
        k3:"k33",
        k4:"k44",
        k5:"k55",
        k6:"k66",
        k7:"k77",
        k8:"k88",
        k9:"k99",
        k10:"k110",
        k11:"k111",
        k12:"k112",
        k13:"k113",
        k14:"k114"
        );
}