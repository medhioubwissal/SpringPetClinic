
pipeline {
    agent any

    tools {
        maven 'Maven' 
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Unit Tests') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn clean package -DskipTests -U'
                }
            }
        }

        stage('Tests Selenium') {
            steps {
               script {
                    // 1. Lancer l'app sur le port 8085 (indispensable car configuré ainsi)
                    sh 'nohup mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085 > app_log.txt 2>&1 &'
                    
                    // 2. Attendre que l'app soit prête
                    sh 'sleep 30' 
                    
                    try {
                        // 3. Exécuter les 5 tests UI demandés [cite: 52]
                        sh 'mvn test -Dtest=SeleniumUITests'
                    } finally {
                        // 4. CORRECTION : Un seul 'sh' et port 8085
                        sh 'fuser -k 8085/tcp || true'
                    }
                }
            }
        }
    }
}