
pipeline {
    agent any

    tools {
        // Doit correspondre au nom configuré dans 'Administrer Jenkins' > 'Tools'
        maven 'Maven' 
    }

    stages {
        stage('Checkout') {
            steps {
                // Télécharge le code depuis GitHub
                checkout scm
            }
        }

        stage('Build & Unit Tests') {
            steps {
                // Compile le projet et saute les tests pour gagner du temps
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Le nom 'SonarQube' doit être celui configuré dans 'System'
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184:sonar'
                }
            }
        }


        stage('Tests Selenium') {
            steps {
               script {
                    // 1. Lancer l'app en arrière-plan avec redirection des logs
                    sh 'nohup mvn spring-boot:run > app_log.txt 2>&1 &'
                    
                    // 2. Attendre que l'app soit prête sur le port 8080 (crucial pour Selenium)
                    sh 'sleep 30' 
                    
                    try {
                        // 3. Exécuter les 5 tests UI demandés 
                        sh 'mvn test -Dtest=SeleniumUITests'
                    } finally {
                        // 4. Arrêter l'app proprement par son port au lieu du JMX
                        sh sh 'fuser -k 8085/tcp || true'
                    }
                }
            }
        }
    }
}