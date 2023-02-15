@Library("shared-library") _
pipeline {
    agent any 
    stages {
        stage('Example') { 
            steps {
                script {
                    log.info "hello world"
                }
            }
        }
    }
}