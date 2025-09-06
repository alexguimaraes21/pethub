pipeline {
    agent any

    environment {
        // Altere para o seu registro e nome da imagem
        DOCKER_REGISTRY = 'registry.gitlab.com'
        IMAGE_NAME = 'pet-hub'
        VERSION = "1.0.${BUILD_NUMBER}" // Usando o número da build do Jenkins para versionar
    }

    stages {
        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Multi-Arch Images') {
            steps {
                script {
                    // Use o 'withCredentials' para acessar as credenciais do Jenkins
                    withCredentials([usernamePassword(credentialsId: 'e5240369-fe46-447d-8e3e-b9d34eab3250', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh """
                        # Loga no Docker Registry usando as variáveis de ambiente injetadas
                        docker login ${DOCKER_REGISTRY} --u ${DOCKER_USER} -p ${DOCKER_PASS}

                        # Cria a imagem para a arquitetura RISC (linux/arm64)
                        #docker buildx build --platform linux/arm64 -f Dockerfile.risc -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${VERSION}-risc --push .

                        # Cria a imagem para a arquitetura CISC (linux/amd64)
                        docker buildx build --platform linux/amd64 -f Dockerfile.cisc -t ${DOCKER_REGISTRY}/${IMAGE_NAME}:${VERSION}-cisc --push .
                        """
                    }

                }
            }
        }
    }

    post {
        always {
            // Limpa o ambiente
            sh 'docker logout ${DOCKER_REGISTRY}'
        }
    }
}