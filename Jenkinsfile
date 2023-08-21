pipeline {
    agent any

    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            steps {
                git 'https://github.com/bobbyrabia/banquito-ws-client.git'
                sh 'chmod +x ./mvnw'
                sh './mvnw clean compile'
            }
        }
    }
}
