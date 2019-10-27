pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build') {
          agent any
          steps {
            bat 'gradlew.bat build'
          }
        }
        stage('error') {
          steps {
            echo 'building'
          }
        }
      }
    }
    stage('Test') {
      steps {
        sh './gradlew cleanTest test'
      }
    }
    stage('Print Success') {
      steps {
        echo 'Build and test succeeded!'
      }
    }
  }
}