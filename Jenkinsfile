pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

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
                echo 'Etapa 9: Compilando la aplicación...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Etapa 10: Ejecutando pruebas unitarias de JUnit...'
                sh 'mvn test'
            }
        }

        stage('Análisis de calidad') {
            steps {
                echo 'Etapa 11: Ejecutando análisis de SonarQube...'
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar'
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
