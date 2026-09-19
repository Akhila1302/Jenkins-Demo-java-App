pipeline {

    environment {
        APP_NAME = 'jenkins-cicd-demo'
        APP_VERSION = '1.0.0'
        AWS_REGION = 'us-east-1'
        ECR_REPO = '181250799935.dkr.ecr.us-east-1.amazonaws.com/jenkins-cicd-demo'

    }

    parameters {
        choice(
        name : 'Environment',
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

        stage('Build Image') {
            steps {
                script {
                    env.IMAGE_TAG = "build-${BUILD_NUMBER}"
                    env.IMAGE_NAME = "${env.APP_NAME}:${env.IMAGE_TAG}"

                    sh "docker build -t ${env.IMAGE_NAME} ."

                    echo "Built image: ${env.IMAGE_NAME}"
                }
            }
        }

        stage('Push Image to ECR') {
            steps {
                sh '''
                    aws ecr get-login-password --region ${AWS_REGION} | \
                    docker login \
                    --username AWS \
                    --password-stdin ${ECR_REPO}

                    docker tag \
                    ${IMAGE_NAME} \
                    ${ECR_REPO}:${IMAGE_TAG}

                    docker push \
                    ${ECR_REPO}:${IMAGE_TAG}
                '''
            }
        }

        stage('Deploy to DEV') {
            steps {
                sh '''
                    docker pull ${ECR_REPO}:${IMAGE_TAG}

                    docker stop jenkins-cicd-demo-dev || true
                    docker rm jenkins-cicd-demo-dev || true

                    docker run -d \
                        --name jenkins-cicd-demo-dev \
                        -p 8081:8080 \
                        ${ECR_REPO}:${IMAGE_TAG}
                '''
            }
        }

        stage('DEV Health Check') {
            steps {
                sh '''
                    sleep 10

                    curl -f http://localhost:8081/health
                '''
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