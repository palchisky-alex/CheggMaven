pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/playwright/java:v1.32.0-focal'
        }
    }
    tools {
        maven "3.8.1"
    }
    stages {
        stage('Build') {
            steps {
                step {
                    git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                    sh "mvn -Dmaven.test.failure.ignore=true clean package"
                }
                post {
                    success {
                        junit '**/target/surefire-reports/TEST-*.xml'
                        archiveArtifacts 'target/*.jar'
                    }
                }
            }
        }
        stage("Deploy to QA") {
            steps {
                echo("deploy to qa")
            }
        }
        stage('Regression Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/palchisky-alex/CheggMaven'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml"
                }
            }
        }
        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build',
                    reportFiles: 'TestExecutionReport.html',
                    reportName: 'HTML Extent Report',
                    reportTitles: ''
                ])
            }
        }
    }
}
