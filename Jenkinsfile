pipeline {
    agent any

    // build steps init
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
		sleep(3000)
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
		sleep(3000)
            }
        }
        stage('Deploy') {
            steps {
		sleep(5000)
                echo 'Deploying....'
            }
        }
    }
}
