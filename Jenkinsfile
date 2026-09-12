pipeline {
    agent any

    environment {
        IMAGE_NAME = 'devops-taller-api'
        IMAGE_TAG = '1.0.0'
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
                echo 'Etapa 13: Desplegando en AWS con credenciales seguras de Jenkins...'
                withCredentials([usernamePassword(credentialsId: 'aws-credentials', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
                    sh 'echo "Simulando despliegue seguro en AWS usando variables de entorno inyectadas"'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo '¡Pipeline ejecutado con éxito y todas las restricciones cumplidas!'
        }
        failure {
            echo 'Error durante el pipeline. Proceso interrumpido por seguridad.'
        }
    }
}
