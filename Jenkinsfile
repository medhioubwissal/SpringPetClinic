pipeline {
    agent any
    
    tools {
        // Ces noms doivent correspondre à votre configuration Jenkins
        maven 'Maven' 
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                // Étape 1 : Récupérer le code de GitHub [cite: 45, 46]
                checkout scm 
            }
        }
        
        stage('Build & Unit Tests') {
            steps {
                // Étape 2 : Compiler avec Maven [cite: 47, 48]
                sh 'mvn clean package'
            }
        }
    }
}
