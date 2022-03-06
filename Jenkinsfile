pipeline {

    agent any
    environment {
//         registry = "vladstep/${PARAM_MODULE}"
        registry = "charlie.lan:5000/ropt/${PARAM_MODULE}"
        imageTag = "latest"
        dockerImage = ''
        registryCredential = 'dockerhub_id'
    }

    parameters {
        choice(name: "PARAM_MODULE",
                choices: ["ropt-reactive-caller", "ropt-receiver", "ropt-receiver-reactive", "ropt-localizer", "ropt-localizer-reactive"],
                description: "Module to be built")
        string(name: "PARAM_BRANCH", defaultValue: "main", trim: true, description: "branch name")
        booleanParam name: 'DEPLOY_PARAM', defaultValue: true, description: "should deploy"
    }

    stages {

        stage('Maven build') {
            steps {
                git branch: params.PARAM_BRANCH, poll: false, url: 'https://github.com/VladPetre/reactive-optimization-test.git'
                dir("${params.PARAM_MODULE}") {
                    sh '''
                      mvn clean install -DskipTests
                  '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                dir("${params.PARAM_MODULE}") {
                    script {
//                         docker.withRegistry('', registryCredential) {
                        docker.withRegistry('', '') {
                            dockerImage = docker.build registry + ":" + imageTag
                        }
                    }
                }
            }
        }

        stage('Docker push') {
            steps {
                script {
//                     docker.withRegistry('', registryCredential) {
                    docker.withRegistry('', '') {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Kube Deploy') {
            when {
                beforeAgent true
                expression {
                    return params.DEPLOY_PARAM
                }
            }

            steps {
                withKubeConfig([credentialsId: 'k3s-sierra-config', serverUrl: 'https://192.168.7.153:6443']) {
                    dir("${params.PARAM_MODULE}") {
                    sh '''
                      kubectl apply -f deployment-service.yml
                    '''
                    }
                }
            }
        }
    }

    post {
        always {
            sh "docker rmi $registry:$imageTag"
            cleanWs()
        }
    }
}