pipeline{
    agent any

    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            steps {
                git 'https://github.com/bobbyrabia/banquito-ws-client.git'
                sh './mvnw clean compile test'
            }
        }
    }
}
