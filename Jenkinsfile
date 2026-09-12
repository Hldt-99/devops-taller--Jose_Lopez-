pipeline {
    agent any

    environment {
        IMAGE_NAME = 'devops-taller-api'
        IMAGE_TAG = '1.0.0'
        AWS_REGION = 'us-east-2'           # <-- Reemplaza con tu región de AWS si es diferente
        AWS_ACCOUNT_ID = '219836849084'     # <-- REEMPLAZA CON TU ID DE CUENTA AWS (12 dígitos)
    }

    triggers {
        pollSCM('H/5 * * * *')
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
                echo 'Etapa 9: Compilando y empaquetando el JAR con Maven...'
                sh 'mvn clean package -DskipTests'
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
                echo 'Etapa 11: Ejecutando análisis de SonarQube con credenciales del sistema...'
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.host.url=http://sonarqube:9000'
                }
            }
        }

        stage('Empaquetado') {
            steps {
                echo 'Etapa 12: Construyendo la imagen Docker ligera...'
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Despliegue') {
            steps {
                echo 'Etapa 13: Autenticando y subiendo la imagen a AWS ECR...'
                withCredentials([usernamePassword(
                    credentialsId: 'aws-credentials', 
                    passwordVariable: 'AWS_SECRET_ACCESS_KEY', 
                    usernameVariable: 'AWS_ACCESS_KEY_ID'
                )]) {
                    sh """
                        # Autenticación de Docker contra ECR
                        aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com

                        # Etiquetado para ECR
                        docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}:${IMAGE_TAG}

                        # Subida a ECR
                        docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo '¡Pipeline ejecutado con éxito e imagen publicada en AWS ECR!'
        }
        failure {
            echo 'Error durante el pipeline. Proceso interrumpido por seguridad.'
        }
    }
}
