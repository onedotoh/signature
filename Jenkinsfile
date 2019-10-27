pipeline {
  agent any
  stages {
    stage('Build') {
      agent {
        docker {
          image 'gradle:jdk11'
        }

      }
      steps {
        sh './gradlew wrapper'
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