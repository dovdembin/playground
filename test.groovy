@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.meterCounter(
        endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
        counter:"tridevlab.test-counter",
        koko:"1", 
        k44:"2",
        barbapapa:"3",
        k1:"4",
        k2:"5",
        k3:"6",
        k4:"7",
        k5:"8",
        k6:"9",
        k7:"10",
        k8:"11",
        k9:"12",
        k10:"13",
        k11:"13",
        k12:"14",
        k13:"15",
        k14:16
        );
}