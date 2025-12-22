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
        // Le flag -DskipTests permet de vérifier que la compilation seule fonctionne
        sh 'mvn clean package -DskipTests=true'
        }
}
    }
}
