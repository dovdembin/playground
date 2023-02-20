@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.meterCounter(
        endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
        counter:"tridevlab.test-counter",
        koko:"aa", 
        k44:"bb",
        barbapapa:"cc",
        k1:"dd",
        k2:"ee",
        k3:6,
        k4:"ff",
        k5:"gg",
        k6:"hh",
        k7:"ii",
        k8:"jj",
        k9:"kk",
        k10:"ll",
        k11:"mm",
        k12:"nn",
        k13:"oo",
        k14:16
        );
}