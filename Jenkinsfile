pipeline {
    agent any

    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            steps {
                sh 'chmod +x ./mvnw'
                sh './mvnw clean compile'
            }
        }
    }
}
