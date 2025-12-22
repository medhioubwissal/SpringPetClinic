
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
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}