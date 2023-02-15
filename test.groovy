pipeline {
    agent any 
    stages {
        stage('Example') { 
            steps {
                global() 
            }
        }
    }
}