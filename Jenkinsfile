pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    stages {

        stage('Maven Clean Compile') {
            steps {
                echo 'Running Maven Clean and Compile'
                sh 'mvn clean compile'
            }
        }

        stage('Maven Install') {
            steps {
                echo 'Running Maven Install'
                sh 'mvn install -DskipTests'
            }
        }

        stage('Build Package') {
            steps {
                echo 'Building Maven Package'
                sh 'mvn package'
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

        stage('JaCoCo Coverage Publishing') {
            steps {
                jacoco execPattern: '**/target/jacoco.exec',
                       classPattern: '**/classes',
                       sourcePattern: '**/src',
                       exclusionPattern: '/target/**/,**/*Test,**/*_javassist/**'
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
                    def dockerImage = docker.build("zaymen/kaddem:0.0.1")
                }
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Logging into DockerHub and Pushing Image'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                        sh 'docker push zaymen/kaddem:0.0.1'
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

        stage('Deploy to k8s'){
            steps{
                script{
                    kubernetesDeploy (configs: 'deployment.yml',kubeconfigId: 'k8sconfigpwd')
                }
            }
        }

    }

    post {

        success {
            mail to: 'aymen.donga@gmail.com',
                 subject: "Pipeline Success: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: "Build ${env.BUILD_NUMBER} was successful!"
        }

        failure {
            mail to: 'aymen.donga@gmail.com',
                 subject: "Pipeline Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                 body: "Build ${env.BUILD_NUMBER} failed. Check Jenkins for more details."
        }
    }
}