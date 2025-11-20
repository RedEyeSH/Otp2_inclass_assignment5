pipeline {
    agent any

    tools {
        maven 'ChocoMaven' // Your configured Maven installation in Jenkins
    }

    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'
        PATH = "${JAVA_HOME}\\bin;C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        SONAR_TOKEN = credentials('SONAR_TOKEN_ID') // Use the Jenkins secret credential ID
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_REPO = 'RedEyeSH/Otp2_inclass_assignment5'
        DOCKER_IMAGE_TAG = 'latest'
        SONAR_HOST_URL = 'http://<SONARQUBE_SERVER_IP>:9000' // Replace with actual reachable IP/host
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/RedEyeSH/Otp2_inclass_assignment5.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                bat """
                    mvn sonar:sonar ^
                        -Dsonar.projectKey=Otp2_week5 ^
                        -Dsonar.projectName=Otp2_week5 ^
                        -Dsonar.host.url=${env.SONAR_HOST_URL} ^
                        -Dsonar.login=${env.SONAR_TOKEN} ^
                        -Dsonar.java.binaries=target/classes
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            deleteDir()
        }

        success {
            echo 'Pipeline completed successfully.'
        }

        failure {
            echo 'Pipeline failed. Check the logs above.'
        }
    }
}
