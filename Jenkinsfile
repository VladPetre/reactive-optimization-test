pipeline {

    environment {
        registry = "vladstep/ropt-caller"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }

    agent any

    stages {

        stage('clean workspace') {
            steps {
                cleanWs()
            }
        }
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

        stage('Building docker image') {
            steps {
				sh '''
					cp reactive-optimization-test/ropt-caller/Dockerfile ./Dockerfile
				'''
                script {
                    dockerImage = docker.build registry + ":1.$BUILD_NUMBER"
                }
            }
        }

        stage('push docker image') {
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
                sh "docker rmi $registry:1.$BUILD_NUMBER"
				//cleanWs()
            }
        }

        stage('Deploy App to Kube') {
              steps {
                sh '''
                    cp reactive-optimization-test/ropt-caller/deployment-service.yml ./deployment-service.yml
                '''
                script {
                  kubernetesDeploy(configs: "deployment-service.yml", kubeconfigId: "localkubeconfig")
                }
              }
        }
    }
}