pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        bat 'gradlew.bat build'
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