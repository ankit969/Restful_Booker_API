pipeline {

    agent any

    tools {
        maven 'Maven3'   // configure in Jenkins global tools
        jdk 'JDK11'
    }

    environment {
        ENV = 'qa'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/your-repo/api-automation.git'
            }
        }

        stage('Clean & Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'target/*.html', fingerprint: true
            }
        }
    }

    post {

        always {
            echo 'Pipeline execution completed'
        }

        success {
            echo 'All tests passed'
        }

        failure {
            echo 'Some tests failed'
        }
    }
}