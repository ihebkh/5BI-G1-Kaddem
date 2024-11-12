pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Maven Install') {
            steps {
                echo 'Running Maven Install'
                sh 'mvn install -DskipTests'
            }
        }

        stage('JUnit/Mockito Tests') {
            steps {
                echo 'Running Tests with JUnit/Mockito'
                sh 'mvn test'
            }
        }

        stage('JaCoCo Coverage Report') {
            steps {
                echo 'Generating JaCoCo Report'
                sh 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis'
                withCredentials([usernamePassword(credentialsId: 'sonarqube-credentials', usernameVariable: 'SONAR_USER', passwordVariable: 'SONAR_PASS')]) {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.host.url=http://192.168.33.10:9000 \
                        -Dsonar.login=$SONAR_USER \
                        -Dsonar.password=$SONAR_PASS \
                        -Dsonar.exclusions=src/main/java/tn/esprit/spring/kaddem/entities/Contrat.java,src/main/java/tn/esprit/spring/kaddem/entities/Equipe.java,src/main/java/tn/esprit/spring/kaddem/entities/DetailEquipe.java,src/main/java/tn/esprit/spring/kaddem/entities/Etudiant.java,src/main/java/tn/esprit/spring/kaddem/entities/Universite.java,src/main/java/tn/esprit/spring/kaddem/KaddemApplication.java,src/main/java/tn/esprit/spring/kaddem/entities/Niveau.java,src/main/java/tn/esprit/spring/kaddem/entities/Option.java,src/main/java/tn/esprit/spring/kaddem/entities/Specialite.java
                    """
                }
            }
        }


        stage('JaCoCo Coverage Publishing') {
            steps {
                jacoco execPattern: '**/target/jacoco.exec',
                       classPattern: '**/classes',
                       sourcePattern: '**/src',
                       exclusionPattern: '/target/**/,**/*Test,**/*_javassist/**'
            }
        }


        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying Package to Nexus'
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building Docker Image'
                    def dockerImage = docker.build("zaymen/ZouariAYMEN-5BI4-G1-kaddem:0.0.1")
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Logging into DockerHub and Pushing Image'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                        sh 'docker push zaymen/ZouariAYMEN-5BI4-G1-kaddem:0.0.1'
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Deploying with Docker Compose'
                sh 'docker compose up -d'
            }
        }
    }

    post {

        success {
            mail to: 'aymen.donga@gmail.com',
                 subject: "Pipeline Success: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: """
                                       <html>
                                       <body style="font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;">
                                           <div style="background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);">
                                               <h2 style="color: #4CAF50;">✅ Build Success: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}</h2>
                                               <p style="color: #333;">Hello,</p>
                                               <p style="color: #333;">The build <strong>${env.JOB_NAME} #${env.BUILD_NUMBER}</strong> was successful!</p>
                                               <p><a href="${env.BUILD_URL}" style="color: #4CAF50; text-decoration: none;">View Build Details</a></p>
                                               <p style="color: #666;">Thank you, <br> Jenkins CI</p>
                                           </div>
                                       </body>
                                       </html>
                                   """,
                 mimeType: 'text/html'
        }

        failure {
            mail to: 'aymen.donga@gmail.com',
                 subject: "Pipeline Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: """
                                       <html>
                                       <body style="font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px;">
                                           <div style="background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);">
                                               <h2 style="color: #FF5722;">❌ Build Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}</h2>
                                               <p style="color: #333;">Hello,</p>
                                               <p style="color: #333;">Unfortunately, the build <strong>${env.JOB_NAME} #${env.BUILD_NUMBER}</strong> failed.</p>
                                               <p><a href="${env.BUILD_URL}" style="color: #FF5722; text-decoration: none;">View Build Details</a></p>
                                               <p style="color: #666;">Thank you, <br> Jenkins CI</p>
                                           </div>
                                       </body>
                                       </html>
                                   """,
                 mimeType: 'text/html'
        }
    }
}