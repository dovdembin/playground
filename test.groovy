// pipeline {
//     agent any

//     tools {
//         // Install the Maven version configured as "M3" and add it to the path.
//         maven "M3"
//     }

//     stages {
//         stage('Build') {
//             steps {
//                 // Get some code from a GitHub repository
//                 git branch: 'main', url: 'https://github.com/dovdembin/otelcli.git'

//                 // Run Maven on a Unix agent.
//                 sh "mvn -DskipTests=true clean package shade:shade"

//                 // To run Maven on a Windows agent, use
//                 // bat "mvn -Dmaven.test.failure.ignore=true clean package"
//                 sh 'java -jar target/otel-jar-with-dependencies.jar -e http://172.30.48.1:4317 -c tridevlab.test-counter'
//             }

//             post {
//                 // If Maven was able to run the tests, even if some of the test
//                 // failed, record the test results and archive the jar file.
//                 success {
//                     archiveArtifacts 'target/*'
//                 }
//             }
//         }
//     }
// }





@Library("shared-library") _
  
// node { 
    
//     stage('Build') { 
        
//         // git branch: 'main', url: 'git@eos2git.cec.lab.emc.com:Test-and-Automation-Enablement/OtelCli.git'
//         // cmd="/usr/local/maven/bin/mvn -B -DskipTests=true clean package shade:shade"
//         // println "${OTEL_EXPORTER_OTLP_ENDPOINT}"
//         // str = sh(script: cmd, returnStdout: true, label: "building otel")
//         // print str
//         // sh 'java -jar target/otel-jar-with-dependencies.jar -e "${OTEL_EXPORTER_OTLP_ENDPOINT}" -c tridevlab.test-counter'

//         // Get some code from a GitHub repository
//         git branch: 'main', url: 'git@eos2git.cec.lab.emc.com:Test-and-Automation-Enablement/OtelCli.git'

//         def mvnHome = tool name: 'M3', type: 'maven'
//         cmd="${mvnHome}/bin/mvn -B -DskipTests=true clean package shade:shade"
//         str = sh(script: cmd, returnStdout: true, label: "maven pachage")
//         // Run Maven on a Unix agent.
//         // sh "mvn -DskipTests=true clean package shade:shade"

//         // To run Maven on a Windows agent, use
//         // bat "mvn -Dmaven.test.failure.ignore=true clean package"
//         sh "java -jar target/otel-jar-with-dependencies.jar -e ${env.OTEL_EXPORTER_OTLP_ENDPOINT} -c tridevlab.test-counter"
             
//     }
// }

 node {  
    stage('Build') { 
        
        // sh(script: "java -jar otel.jar -e http://172.30.48.1:4317 -c tridevlab.test-counter", label: "verify_content.sh") 
        // git branch: 'main', url: 'https://github.com/dovdembin/otelcli.git'

                // Run Maven on a Unix agent.
        // withMaven {
        //     sh "mvn clean verify"
        // } // withMa
                // config_params       = ['xpoolAllocation':'--lg 1 -l MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,PhysicalLG']
                // bpt_labels=otel.getLabels(config_params['xpoolAllocation'])
                // println "${bpt_labels}"
                // // println "${JENKINS_HOME}/jobs/${JOB_NAME}/builds/${BUILD_NUMBER}/archive/target/"
                // dir("${JENKINS_HOME}/jobs/${JOB_NAME}/builds/${BUILD_NUMBER}/archive/target/"){

                //     sh 'curl -k -O "https://afeoscyc-mw.cec.lab.emc.com/artifactory/testsign/otel-jar-with-dependencies.jar"'
                //     sh """
                //         java -jar otel-jar-with-dependencies.jar -e "${OTEL_EXPORTER_OTLP_ENDPOINT}" -sig metric -m counter -n tridevlab.test-counter \
                //         -l test.testlabel="${bpt_labels}" \
                //         -a test.name=koko 
                //     """
                // }
    def config_params = ['xpoolAllocation':'--lg 1 -l @indus1\\|@indus2\\|@indus3,MLK']

    println otel.checkLabels(config_params['xpoolAldlocation'], "");
        }
    // def map = [
    //     endpoint:env.OTEL_EXPORTER_OTLP_ENDPOINT, 
    //     counter:"tridevlab.test-counter", 
    //     "test.name":"aa", 
    //     "test.bpt-suite":"bb",
    //     "test.bpt-merge-candidate":"cc",
    //     "test.duration":3456,
    //     "test.source":"ee",
    //     "test.agent-name":"moshe",
    //     "test.ibid":"ff",
    //     "test.program-branch":"gg",
    //     "test.flavor":"hh",
    //     "test.github.pr.id":"ii",
    //     "test.github.pr.url":"jj",
    //     "test.github.pr.author.login":"kk",
    //     "test.github.pr.author.email":"ll",
    //     "test.github.source-branch":"mm",
    //     "test.github.target-branch":"nn",
    //     "test.package-url":"oo",
    //     "test.slave-name":"oo",
    //     "test.jenkins.build-number":16,
    //     "test.jenkins.build-url":"gdg",
    //     "test.dingo.unique-id":"eee",
    //     "test.sequential.appliance":"ttt",
    //     "test.cyc-test":"r5",
    //     "test.status":"44re"
    //     ]
    //     println env.OTEL_EXPORTER_OTLP_ENDPOINT
    
    
}


 


    
