pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Checkout Git repository') {
            steps {
                echo 'Pulling'
                git branch: 'khmiriiheb-5BI4-G1', url: 'https://github.com/ihebkh/5BI4-G1-Kaddem.git'
            }
        }

        stage('Status Mysql') {
            steps {
                script {
                    sh 'sudo systemctl start mysql'
                }
            }
        }

        stage('Maven Clean Compile') {
            steps {
                sh 'mvn clean'
                echo 'Running Maven Compile'
                sh 'mvn compile'
            }
        }

        stage('Maven Install') {
            steps {
                sh 'mvn install'
            }
        }

        stage('Build package') {
            steps {
                sh 'mvn package'
            }
        }


        stage('Tests - JUnit/Mockito') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy to Nexus') {
               steps {
                   sh 'mvn deploy'
               }
           }



        stage('Rapport JaCoCo') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('JaCoCo coverage report') {
            steps {
                step([$class: 'JacocoPublisher',
                      execPattern: '**/target/jacoco.exec',
                      classPattern: '**/classes',
                      sourcePattern: '**/src',
                      exclusionPattern: '*/target/**/,**/*Test*,**/*_javassist/**'
                ])
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.10:9001 -Dsonar.login=admin -Dsonar.password=201JmT1896@@'
            }
        }
    }

    post {
        success {
            mail bcc: '', body: 'Final Report: The pipeline has completed successfully. No action required.',
                 cc: '', from: '', replyTo: '', subject: 'Succès de la pipeline DevOps',
                 to: 'khmiriiheb3@gmail.com'
        }
        failure {
            mail bcc: '', body: 'The pipeline has failed. Please check the Jenkins logs for details.',
                 cc: '', from: '', replyTo: '', subject: 'Échec de la pipeline DevOps',
                 to: 'khmiriiheb3@gmail.com'
        }
    }
}
