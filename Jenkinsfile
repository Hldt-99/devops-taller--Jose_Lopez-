pipeline {
    agent any

    environment {
        IMAGE_NAME = 'devops-taller-api'
        IMAGE_TAG = '1.0.0'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Etapa 8: Clonando código fuente desde GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Etapa 9: Compilando la aplicación con Maven en Docker...'
                sh 'docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Etapa 10: Ejecutando pruebas unitarias de JUnit...'
                sh 'docker run --rm -v "$PWD":/app -w /app maven:3.9.6-eclipse-temurin-17 mvn test'
            }
        }

        stage('Análisis de calidad') {
            steps {
                echo 'Etapa 11: Ejecutando análisis de SonarQube...'
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'docker run --rm -v "$PWD":/app -w /app --network host maven:3.9.6-eclipse-temurin-17 mvn sonar:sonar -Dsonar.host.url=http://localhost:9000'
                }
            }
        }

        stage('Empaquetado') {
            steps {
                echo 'Etapa 12: Construyendo la imagen Docker multi-etapa...'
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Despliegue') {
            steps {
                echo 'Etapa 13: Preparando el despliegue en AWS...'
                sh 'echo "API empaquetada lista para despliegue en AWS"'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo '¡Las 6 etapas del pipeline se ejecutaron con éxito!'
        }
        failure {
            echo 'Error durante la ejecución del pipeline.'
        }
    }
}
