@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.meterCounter(
        endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
        counter:"tridevlab.test-counter"
        koko:"loko", 
        k44:"bb",
        barba:"papa",
        k1:"k1",
        k2:"k2",
        k3:"k3",
        k4:"k4",
        k5:"k5",
        k6:"k6",
        k7:"k7",
        k8:"k8",
        k9:"k9",
        k10:"k10",
        k11:"k11",
        k12:"k12",
        k13:"k13",
        k14:"k14"
        );
}