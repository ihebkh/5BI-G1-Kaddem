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

        stage('Status Mysql') {
                    steps {
                        script {
                            sh 'sudo systemctl start mysql'
                        }
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
    }
}
