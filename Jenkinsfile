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
                sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.10:9000 -Dsonar.login=admin -Dsonar.password=201JmT1896@@'
            }
        }
          stage('Email Notification') {
                    steps{
        mail bcc: '', body: '''Stage: GIT Pull
         - Pulling from Git...

        Stage: Maven Clean Compile
         - Building Spring project...

        Stage: Test - JUNIT/MOCKITO
         - Testing Spring project...

        Stage: JaCoCo Report
         - Generating JaCoCo Coverage Report...

        Stage: SonarQube Analysis
         - Running Sonarqube analysis...

        Stage: Deploy to Nexus
         - Deploying to Nexus...

        Stage: Build Docker Image
         - Building Docker image for the application...

        Stage: Push Docker Image
         - Pushing Docker image to Docker Hub...

        Stage: Docker Compose
         - Running Docker Compose...

        Stage: Monitoring Services G/P
         - Starting Prometheus and Grafana...

        Final Report: The pipeline has completed successfully. No action required''', cc: '', from: '', replyTo: '', subject: 'Succès de la pipeline DevOps Project KaddemUniversite BenIsmail', to: 'khmiri.iheb@esprit.tn'
                    }
                }

    }
}
