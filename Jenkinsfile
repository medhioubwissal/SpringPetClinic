pipeline {
    agent any
    
    tools {
        maven 'Maven' 
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm 
            }
        }
        
        stage('Build & Unit Tests') {
            steps {
                // On utilise -DskipTests pour le premier build rapide
                sh 'mvn clean package -DskipTests=true'
            }
        }
    }
}
