pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        script {
          if (isUnix()) {
            sh './gradlew build'
          } else {
            bat 'gradlew.bat build'
          }
        }

      }
    }
    stage('Test') {
      steps {
        bat 'gradlew.bat cleanTest test'
        script {
          if (isUnix()) {
            sh './gradlew cleanTest test'
          } else {
            bat 'gradlew.bat cleanTest test'
          }
        }

      }
    }
    stage('Print Success') {
      steps {
        echo 'Build and test succeeded!'
      }
    }
  }
}