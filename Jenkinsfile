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
                      exclusionPattern: '/target/**/,**/*Test,**/*_javassist/**'
                ])
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.33.10:9000 -Dsonar.login=admin -Dsonar.password=201JmT1896@@'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying to Nexus'
                sh 'mvn clean deploy -DskipTests'
            }
        }
 stage('Build Docker Image ') {
            steps {
                script {
                    sh 'sudo chmod 666 /var/run/docker.sock'
                    def dockerImage=docker.build("ihebkh336/kaddem:0.0.1")
                }
            }

  stage('Deploy Image') {
             steps {
                 script {
                     // Connexion à DockerHub en utilisant le mot de passe en clair
                     sh 'docker login -u ihebkh336 -p a1b2c3IHEB'

                     // Pousser l'image Docker sur DockerHub
                     sh 'docker push ihebkh336/kaddem:0.0.1'
                 }
             }

             stage('Deploy with Docker Compose') {
                         steps {
                             script {
                                 // Assurez-vous que vous êtes dans le répertoire contenant le fichier docker-compose.yml

                                     // Exécutez docker-compose up -d
                                     sh 'docker-compose up -d'

                             }
                         }
        }

    }
}
