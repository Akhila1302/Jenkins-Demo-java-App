pipeline {

    agent any

    tools {
        maven 'mymaven'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                                    sh '''
                                        mvn sonar:sonar \
                                        -Dsonar.projectKey=jenkins-cicd-demo \
                                        -Dsonar.projectName=jenkins-cicd-demo
                                    '''
                                }
            }
        }
    }

    post {

        success {
            echo 'Pipeline completed successfully!!!'
        }

        failure {
            echo 'Pipeline failed!'
        }

        always {
            echo 'Pipeline execution completed.'
        }
    }
}