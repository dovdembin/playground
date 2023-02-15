@Library("shared-library") _
pipeline {
    agent any 
    stages {
        stage('Example') { 
            steps {
                log.info "hello world"
            }
        }
    }
}