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

       stage('Install MySQL') {
           steps {
               echo 'Installing MySQL'
               sh 'sudo apt-get update && sudo apt-get install -y mysql-server'
           }
       }


stage('Check MySQL Service') {
    steps {
        echo 'Checking MySQL Service Status'
        sh '''
            if systemctl list-units --type=service | grep -q mysqld; then
                systemctl status mysqld
            elif systemctl list-units --type=service | grep -q mysql; then
                systemctl status mysql
            elif service --status-all | grep -q mysqld; then
                service mysqld status
            elif service --status-all | grep -q mysql; then
                service mysql status
            else
                echo "MySQL service is not found."
                exit 1
            fi
        '''
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
    }
}
