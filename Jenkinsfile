pipeline {
  agent {
    docker {
      image 'gradle:jdk11'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'gradle build'
      }
    }
    stage('Test') {
      steps {
        sh 'gradle cleanTest test'
      }
    }
    stage('Print Success') {
      steps {
        echo 'Build and test succeeded!'
      }
    }
  }
}