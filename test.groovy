@Library("shared-library") _
pipeline {
    agent any 
    stages {
        stage('Example') { 
            steps {
                global.func(name:"koko", dayOfWeek:"Thursday") 
            }
        }
    }
}