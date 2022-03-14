pipeline {

    agent any
    environment {
        registry = "ropt/${PARAM_MODULE}"
        registryRoot = "http://charlie.lan:5000"
        registryClr = "charlie.lan:5000/ropt/${PARAM_MODULE}"
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
                    docker.withRegistry(registryRoot) {
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
                      deployName="$(grep 'name:' deployment-service.yml | head -1 | awk -F: '{print $2}' | tr -d " ")"
                      kubectl -n ropt delete deployment $deployName
                      sleep 5
                      kubectl apply -f deployment-service.yml
                      kubectl -n ropt rollout status deployment $deployName --watch --timeout=5m
                    '''
                    }
                }
            }
        }
    }

    post {
        always {
            sh "docker rmi $registryClr:$imageTag"
            cleanWs()
        }
    }
}