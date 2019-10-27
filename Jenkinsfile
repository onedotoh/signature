pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build') {
          agent any
          steps {
            sh './gradlew build'
          }
        }
        stage('') {
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