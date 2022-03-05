pipeline {

    agent any
    environment {
        registry = "vladstep/${PARAM_MODULE}"
        imageTag = "latest"
        dockerImage = ''
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
                        dockerImage = docker.build registry + ":" + imageTag
                    }
                }
            }
        }

        stage('Docker push') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
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
                      deployName="$(grep 'name:' deployment-service.yml | head -1 | awk -F: '{print $2}' | tr -d " ")"
                      kubectl rollout status deployment $deployName --watch --timeout=5m
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