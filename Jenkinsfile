pipeline {
    agent any

    stages {
        // Etapa de Checkout
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/username/repo.git'
            }
        }

        // Etapa de Compilação/Build
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
                echo 'Build feito com sucesso!'
            }
        }

        // Etapa de Testes Unitários
        stage('Unit tests') {
            steps {
                sh 'mvn test -Dsurefire.suiteXmlFiles=src/test/resources/unit-test-suite.xml'
                echo 'Testes unitários concluídos!'
            }
        }

        // Etapa de Testes de Integração
        stage('Integration tests') {
            steps {
                sh 'mvn verify -DskipUnitTests -Dfailsafe.suiteXmlFiles=src/test/resources/integration-test-suite.xml'
                echo 'Testes de integração concluídos!'
            }
        }

        // Etapa de Testes de Mutação
        stage('Mutation tests') {
            steps {
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'
                echo 'Testes de mutação concluídos!'
            }
        }

        // Etapa de Deploy
        stage('Deploy') {
            steps {
                script {
                    if (params.DEPLOY_TO_LINUX) {
                        def deployPath = '/path/to/deploy'
                        sh "cp -r target/*.jar $deployPath/"
                        echo 'Deploy para Linux concluído!'
                    }

                    if (params.DEPLOY_TO_DOCKER) {
                        sh 'docker-compose down'
                        sh 'docker-compose up -d --build'
                        echo 'Deploy para Docker concluído!'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline executado'
        }
        success {
            echo 'Pipeline concluído com sucesso!'
        }
        failure {
            echo 'Pipeline falhou.'
        }
    }
}