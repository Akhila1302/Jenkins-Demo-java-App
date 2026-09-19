pipeline {

    environment {
        APP_NAME = 'jenkins-cicd-demo'
        APP_VERSION = '1.0.0'
    }

    parameters {
        choice(
        name : 'Environment'
        choices: ['Dev', 'Int', 'QA', 'Prod'],
        description: 'Select the environment to deploy the application'
        )
    }

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
                        mvn clean verify \
                          org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                          -Dsonar.projectKey=jenkins-cicd-demo \
                          -Dsonar.projectName=jenkins-cicd-demo
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                 timeout(time: 5, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
            }
        }

        stage('Show Environment') {
            steps {
                echo "Selected Environment: ${params.Environment}"
                echo "Application: ${env.APP_NAME}"
                echo "Version: ${env.APP_VERSION}"
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
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