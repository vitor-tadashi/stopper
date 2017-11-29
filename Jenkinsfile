import hudson.model.*
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
node {
      wrap([$class: 'TimestamperBuildWrapper']) {
      stage ('Checkout')
        deleteDir()
        checkout scm
        bat 'del /F /Q outfile '
        bat 'git name-rev --name-only HEAD > outfile'
        def Branch_Name = readFile 'outFile'
        println Branch_Name
        def Branch_Master = "remotes/origin/develop"
        Branch_Name = Branch_Name.trim()
        Branch_Master = Branch_Master.trim()
        if (Branch_Name == Branch_Master) {
           sonarqube()
           build()
           postbuild()
        }
        else{
             build()
             unitTest()
             allCodeQualityTests()
             postbuild()
        }    
      }
}
    def sonarqube () {
        stage 'Sonarqube Analisys'
           def pom = readMavenPom()
                writeFile encoding: 'UTF-8', file: 'sonar-project.properties', text: """
                # must be unique in a given SonarQube instance
                sonar.projectKey=$pom.groupId:$pom.artifactId
                sonar.projectName=$pom.artifactId
                sonar.projectVersion=$pom.version
                sonar.genericcoverage.reportPaths=target/surefire-reports/TEST-jenkins.plugins.jsonHelper.FromJsonStepTest.xml
                sonar.sourceEncoding=UTF-8
                sonar.java.source=1.8
				sonar.analysis.mode=preview 
                sonar.issuesReport.html.enable=true
                # Path is relative to the sonar-project.properties file. Replace "\\" by "/" on Windows.
                # Since SonarQube 4.2, this property is optional if sonar.modules is set.
                # If not set, SonarQube starts looking for source code from the directory containing
                # the sonar-project.properties file.
                sonar.sources=src
				sonar.inclusions=**/*.java"""
                archive 'sonar-project.properties'                
                // requires SonarQube Scanner 2.8+
                def jdk8 = tool name: '1.8.0_131'
                env.JAVA_HOME = "${jdk8}"
                def scannerHome = 'C:\\sonar-scanner-2.8';
                withSonarQubeEnv('sonar-pmd') {
                //sh "${scannerHome}/bin/sonar-scanner"
            	bat "${scannerHome}\\bin\\sonar-scanner.bat"
                }
    }    

    def build () {
        stage 'Build'
           def jdk = tool name: '1.8.0_131'
           def mvnHome = tool name: '3.5.0'
           env.JAVA_HOME = "${jdk}"
           bat "${mvnHome}/bin/mvn clean install -B"

    }

    def allTests() {
    stage 'All tests'
    // don't skip anything
    mvn 'test -B'
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    if (currentBuild.result == "UNSTABLE") {
        // input "Unit tests are failing, proceed?"
        sh "exit 1"
       }
    }

    def allCodeQualityTests() {
    stage 'Code Quality'
    lintTest()
    coverageTest()
    }

    def lintTest() {
    context="continuous-integration/jenkins/linting"
    setBuildStatus ("${context}", 'Checking code conventions', 'PENDING')
    lintTestPass = true

    try {
        mvn 'verify -DskipTests=true'
        } catch (err) {
                    setBuildStatus ("${context}", 'Some code conventions are broken', 'FAILURE')
                    lintTestPass = false
        } finally {
        if (lintTestPass) setBuildStatus ("${context}", 'Code conventions OK', 'SUCCESS')
       }
    }

    def coverageTest() {
    context="continuous-integration/jenkins/coverage"
    setBuildStatus ("${context}", 'Checking code coverage levels', 'PENDING')

    coverageTestStatus = true

    try {
        mvn 'cobertura:check'
    } catch (err) {
        setBuildStatus("${context}", 'Code coverage below 90%', 'FAILURE')
        throw err
    }

    setBuildStatus ("${context}", 'Code coverage above 90%', 'SUCCESS')

    }

    def postbuild () {
        stage 'Warnings-Publisher'
           warnings canComputeNew: false, canResolveRelativePaths: false, consoleParsers: [[parserName: 'Java Compiler (javac)'], [parserName: 'JavaDoc Tool'], [parserName: 'Maven']], defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', unHealthy: '' 
        
        //stage 'Publish HMTL Sonar'
		   //publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: '', reportFiles: 'issues-report-light.html', reportName: 'SonarQube Report', reportTitles: '${BUILD_ID}-issues-report-light.html'])

		
        stage 'Archive'
           step([$class: 'ArtifactArchiver', artifacts: '**/*.war', fingerprint: true])
                   
       // stage 'Delete Workpspace'
       //    deleteDir()
    }

    def deploy () {     
          stage 'Deploy'
             step ([$class: 'CopyArtifact',projectName: 'projetos-verity/Build/pipeline-pause',filter: 'src/pause-web/target/pause-web.war']);
             sh 'ssh pause@192.168.2.175 "sudo service pause stop; sudo mv /opt/pause/pause-web.war /tmp/bkp/pause/pause-web.war"'
             sh 'scp ./src/pause-web/target/pause-web.war pause@192.168.2.175:/opt/pause'
             sh 'ssh pause@192.168.2.175 sudo service pause start'
      }