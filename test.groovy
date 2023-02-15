@Library("shared-library") _
pipeline {
    agent any 
    stages {
        stage('Example') { 
            steps {
                global("koko", "Thursday") 
            }
        }
    }
}