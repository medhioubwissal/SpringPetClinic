
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
                    // Lancer l'app en arrière-plan pour que Selenium puisse y accéder
                    sh 'mvn spring-boot:start' 
                    try {
                        // --Exécuter les tests TestNG
                        sh 'mvn test -Dtest=SeleniumUITests'
                    } finally {
                        // --Toujours arrêter l'app, même si les tests échouent--
                        sh 'mvn spring-boot:stop'
                    }
                }
            }
        }
    }
}