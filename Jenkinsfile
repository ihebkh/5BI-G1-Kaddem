pipeline {
    agent any
    tools {
        maven 'M2_HOME'  // Make sure Maven is correctly configured in Jenkins
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'khmiriiheb-5BI4-G1',
                    url: 'https://github.com/ihebkh/5BI4-G1-Kaddem.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=201JmT1896@@ -Dmaven.test.skip=true'
            }
        }
    }
}