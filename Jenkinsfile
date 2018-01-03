pipeline {
    agent any

    // build steps init
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
		sleep(3)
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
		sleep(3)
            }
        }
        stage('Deploy') {
            steps {
		sleep(5)
                echo 'Deploying....'
            }
        }
    }
}
