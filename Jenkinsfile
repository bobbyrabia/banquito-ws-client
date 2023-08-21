pipeline {
    agent any

    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            steps {
                sh './mvnw clean compile'
            }
        }
    }
}
