@Library("shared-library") _
pipeline {
    agent any 
    stages {
        stage('Example') { 
            steps {
                global(name:"koko", dayOfWeek:"Thursday") 
            }
        }
    }
}