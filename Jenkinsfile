pipeline {
    agent any

    tools {
        maven 'maven'
    }
    stages {
        stage('test') {
            steps {
                sh 'mvn clean compile test'
            }
        }
    }
}
