pipeline {
    agent any
    tools {
        maven 'ChocoMaven'
    }

    environment {
     PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
           JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'  // Adjust to your actual JDK pat
        SONARQUBE_SERVER = 'SonarQubeServer'  // The name of the SonarQube server configured in Jenkins
        SONAR_TOKEN = 'sqp_d2a094926a0769324095928a981e2e81b8570d8f' // Store the token securely
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_REPO = 'RedEyeSH/Otp2_inclass_assignment5'
        DOCKER_IMAGE_TAG = 'latest'


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
                withSonarQubeEnv('SonarQubeServer') {
                    bat """
                        ${tool 'SonarScanner'}\\bin\\sonar-scanner ^
                        -Dsonar.projectKey=devops-demo ^
                        -Dsonar.sources=src ^
                        -Dsonar.projectName=DevOps-Demo ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.login=${env.SONAR_TOKEN} ^
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                    // Or specify Dockerfile path explicitly if needed
                    // docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}", "-f ./Dockerfile .")
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
}
