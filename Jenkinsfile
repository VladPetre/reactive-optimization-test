pipeline {

    tools {
        maven 'M3'
    }
   environment {
        registry = "vladstep/ropt-caller"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }

    agent any

    stages {
        stage('Cloning Git') {
            steps {
            sh '''
                git clone https://github.com/VladPetre/reactive-optimization-test.git
               '''
            }
        }

        stage('maven build') {
             steps {
                sh '''
                    cd reactive-optimization-test/ropt-caller
                    mvn install
                '''
             }
        }

        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }

        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}