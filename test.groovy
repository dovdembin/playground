@Library("shared-library") _
 
 

stage("Running Stage: ") {
    log.meterCounter(
        endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT,
        counter:"tridevlab.test-counter",
        (test.name):"aa", 
        (test.bpt-suite):"bb",
        (test.bpt-merge-candidate):"cc",
        (test.duration):3456,
        (test.source):"ee",
        (test.agent-name):"moshe",
        (test.ibid):"ff",
        (test.program-branch):"gg",
        (test.flavor):"hh",
        (test.github.pr.id):"ii",
        (test.github.pr.url):"jj",
        (test.github.pr.author.login):"kk",
        (test.github.pr.author.email):"ll",
        (test.github.source-branch):"mm",
        (test.github.target-branch):"nn",
        (test.package-url):"oo",
        (test.slave-name):"oo",
        (test.jenkins.build-number):16,
        (test.jenkins.build-url):"gdg",
        (test.dingo.unique-id):"eee",
        (test.sequential.appliance):"ttt",
        (test.cyc-test):"r5",
        (test.status):"44re"
        );
}