pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'gradle wrapper'
        sh './gradlew build'
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